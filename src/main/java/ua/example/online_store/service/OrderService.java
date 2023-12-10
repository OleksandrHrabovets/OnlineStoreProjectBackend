package ua.example.online_store.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.webjars.NotFoundException;
import ua.example.online_store.model.Cart;
import ua.example.online_store.model.Currency;
import ua.example.online_store.model.Order;
import ua.example.online_store.model.OrderItem;
import ua.example.online_store.model.Photo;
import ua.example.online_store.model.SKUCharacteristic;
import ua.example.online_store.model.enums.OrderStatus;
import ua.example.online_store.repository.OrderItemRepository;
import ua.example.online_store.repository.OrderRepository;
import ua.example.online_store.service.email.EmailService;
import ua.example.online_store.web.dto.OrderDeliveryDto;
import ua.example.online_store.web.dto.OrderItemRecord;

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
  private final TemplateEngine templateEngine;

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

    String message = templateEngine.process("email-order-details-customer", getContext(order));

    emailService.sendEmail(
        order.getDelivery().getEmail(),
        "Підтвердження Замовлення №%s".formatted(order.getId()),
        message);
  }

  private void sendOrderMessageToSalesManager(Order order) {

    String message = templateEngine.process("email-order-details-manager", getContext(order));

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

  private Context getContext(Order order) {
    Currency currency = order.getItems().get(0).getSku().getProduct().getPrice().getCurrency();

    Context context = new Context();
    context.setVariable("orderId", order.getId());
    context.setVariable("orderStatus", getStatusText(order.getStatus()));
    context.setVariable("orderItems", getOrderItemRecords(order));
    context.setVariable("totalAmount", order.getTotalAmount());
    context.setVariable("currencyCode", currency.getCode());
    context.setVariable("firstName", order.getDelivery().getFirstName());
    context.setVariable("lastName", order.getDelivery().getLastName());
    context.setVariable("customerPhoneNumber", order.getDelivery().getCustomerPhoneNumber());
    context.setVariable("address", order.getDelivery().getAddress());
    return context;
  }

  private static String getStatusText(OrderStatus status) {
    return switch (status) {
      case NEW -> "Прийняте в обробку";
      case PROCESSING -> "В роботі";
      default -> "Невідомий статус";
    };
  }

  public List<OrderItemRecord> getOrderItemRecords(Order order) {
    return order.getItems().stream()
        .map(orderItem -> new OrderItemRecord(orderItem.getSku().getProduct().getTitle(),
            orderItem.getSku().getCharacteristics().stream()
                .filter(skuCharacteristic -> skuCharacteristic.getCharacteristic().getTitle()
                    .equals("color")).findFirst().orElse(
                    SKUCharacteristic.builder().value("").build()).getValue(),
            orderItem.getSku().getCharacteristics().stream()
                .filter(skuCharacteristic -> skuCharacteristic.getCharacteristic().getTitle()
                    .equals("size")).findFirst().orElse(
                    SKUCharacteristic.builder().value("").build()).getValue(),
            orderItem.getSku().getProduct().getPhotos().stream()
                .findFirst().orElse(Photo.builder().url("").build()).getUrl(),
            orderItem.getPrice(),
            orderItem.getQuantity(),
            orderItem.getAmount())
        ).toList();
  }
}
