package ua.example.online_store.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ua.example.online_store.model.Cart;
import ua.example.online_store.model.Order;
import ua.example.online_store.model.OrderItem;
import ua.example.online_store.model.OrderStatus;
import ua.example.online_store.repository.OrderItemRepository;
import ua.example.online_store.repository.OrderRepository;
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


  public List<Order> getAll() {
    log.info(INVOKED_METHOD, "getAll()");
    return orderRepository.findAll();
  }

  public List<Order> getBySessionId(String sessionId) {
    log.info(INVOKED_METHOD, "getBySessionId()");
    return orderRepository.findAllBySessionId(sessionId);
  }

  @Transactional
  public void makeOrder(String sessionId, OrderDeliveryDto orderDeliveryDto) {
    log.info(INVOKED_METHOD, "makeOrder()");

    Cart cart = cartService.getBySessionId(sessionId)
        .orElseThrow(() -> new NotFoundException("Cart not found"));

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
    });

    order.setStatus(OrderStatus.NEW);
    order.setDelivery(orderDeliveryService.save(orderDeliveryDto));
    orderRepository.save(order);
  }

  public Order changeOrderStatus(Long orderId, OrderStatus status) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new NotFoundException("Order not found"));
    order.setStatus(status);
    return orderRepository.save(order);
  }
}
