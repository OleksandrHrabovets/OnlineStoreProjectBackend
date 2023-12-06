package ua.example.online_store.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ua.example.online_store.model.Cart;
import ua.example.online_store.model.Currency;
import ua.example.online_store.model.Order;
import ua.example.online_store.model.OrderItem;
import ua.example.online_store.model.enums.OrderStatus;
import ua.example.online_store.repository.OrderItemRepository;
import ua.example.online_store.repository.OrderRepository;
import ua.example.online_store.service.email.EmailService;
import ua.example.online_store.web.dto.OrderDeliveryDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

  public static final String INVOKED_METHOD = "invoked method {}";
  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final CartService cartService;
  private final OrderDeliveryService orderDeliveryService;
  private final SKUService skuService;
  private final AvailableQuantityService availableQuantityService;
  private final EmailService emailService;

  @Value("${app.email-sales-manager}")
  private String emailSalesManager;

  public List<Order> getAll() {
    log.info(INVOKED_METHOD, "getAll()");
    return orderRepository.findAll();
  }

  public List<Order> getBySessionId(String sessionId) {
    log.info(INVOKED_METHOD, "getBySessionId()");
    return orderRepository.findAllBySessionId(sessionId);
  }

  @Transactional
  public Order makeOrder(String sessionId, OrderDeliveryDto orderDeliveryDto) {
    log.info(INVOKED_METHOD, "makeOrder()");

    Cart cart = cartService.getBySessionId(sessionId)
        .orElseThrow(() -> new NotFoundException("Cart not found"));

    cart.getItems().forEach(cartItem ->
        availableQuantityService.reduceAvailableQuantity(cartItem.getSku(),
            cartItem.getQuantity()));

    Order order = Order.builder()
        .sessionId(sessionId)
        .items(new ArrayList<>())
        .build();

    cart.getItems().forEach(cartItem -> {
      OrderItem orderItem = OrderItem.builder()
          .order(order)
          .sku(cartItem.getSku())
          .price(BigDecimal.ZERO)
          .quantity(BigDecimal.ZERO)
          .amount(BigDecimal.ZERO)
          .build();
      orderItem.setPrice(cartItem.getPrice());
      orderItem.setQuantity(cartItem.getQuantity());
      orderItem.setAmount(cartItem.getAmount());
      orderItemRepository.save(orderItem);
      order.getItems().add(orderItem);
    });

    order.setStatus(OrderStatus.NEW);
    order.setDelivery(orderDeliveryService.save(orderDeliveryDto));
    orderRepository.save(order);

    cartService.clear(sessionId);
    sendOrderConfirmationAndNotification(order);
    sendOrderMessageToSalesManager(order);
    return order;
  }

  private void sendOrderConfirmationAndNotification(Order order) {
    Currency currency = order.getItems().get(0).getSku().getProduct().getPrice().getCurrency();

    String message = order.getItems().stream()
        .reduce(
            """
                Замовлення №%s
                Статус: %s
                Дякуємо за ваше замовлення в інтернет магазині ZATYSHNA
                                
                """
                .formatted(order.getId(), "Прийняте в обробку"),
            (s, orderItem) -> s + "\n   -"
                + orderItem.getSku().getProduct().getTitle()
                + "(" + skuService.skuCharacteristicsToString(orderItem.getSku()) + ")"
                + "\tціна: "
                + orderItem.getPrice()
                + currency.getCode()
                + "\tкількість: "
                + orderItem.getQuantity()
                + "\tсума: "
                + orderItem.getAmount()
                + currency.getCode(),
            String::concat);
    message = message + """
                
        ДОСТАВКА: за тарифами перевізника
        ЗАГАЛОМ: %s %s
                
        Дані отримувача:
        %s %s, %s
        %s
        Спосіб оплати: %s
        """.formatted(order.getItems().stream()
            .map(OrderItem::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add),
        currency.getCode(),
        order.getDelivery().getFirstName(),
        order.getDelivery().getLastName(),
        order.getDelivery().getCustomerPhoneNumber(),
        order.getDelivery().getAddress(),
        "при отриманні");
    emailService.sendEmail(
        order.getDelivery().getEmail(),
        "Підтвердження Замовлення №%s".formatted(order.getId()),
        message);
  }

  private void sendOrderMessageToSalesManager(Order order) {
    Currency currency = order.getItems().get(0).getSku().getProduct().getPrice().getCurrency();

    String message = order.getItems().stream()
        .reduce(
            """
                Замовлення №%s
                Статус: %s
                                
                Дані замовлення:
                """
                .formatted(order.getId(), order.getStatus()),
            (s, orderItem) -> s + "\n   -"
                + orderItem.getSku().getProduct().getTitle()
                + "(" + skuService.skuCharacteristicsToString(orderItem.getSku()) + ")"
                + "\tціна: "
                + orderItem.getPrice()
                + currency.getCode()
                + "\tкількість: "
                + orderItem.getQuantity()
                + "\tсума: "
                + orderItem.getAmount()
                + currency.getCode(),
            String::concat);
    message = message + """
                
        Загальна сума\s: %s %s
                
        Дані доставки:
        Ім’я: \t%s
        Прізвище: \t%s
        Номер телефону: \t%s
        Адреса доставки: \t%s
        Спосіб оплати: %s
        """.formatted(order.getItems().stream()
            .map(OrderItem::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add),
        currency.getCode(),
        order.getDelivery().getFirstName(),
        order.getDelivery().getLastName(),
        order.getDelivery().getCustomerPhoneNumber(),
        order.getDelivery().getAddress(),
        "Післяплата");
    emailService.sendEmail(
        emailSalesManager,
        "Деталі Замовлення №%s".formatted(order.getId()),
        message);
  }

  public Order changeOrderStatus(Long orderId, OrderStatus status) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new NotFoundException("Order not found"));
    order.setStatus(status);
    return orderRepository.save(order);
  }
}
