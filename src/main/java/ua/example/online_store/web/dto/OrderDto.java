package ua.example.online_store.web.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import ua.example.online_store.model.enums.OrderStatus;

@Data
@Builder
public class OrderDto {

  private Long id;
  private String sessionId;
  private BigDecimal totalQuantity;
  private BigDecimal totalAmount;
  private OrderStatus status;
  private OrderDeliveryDto delivery;
  private List<OrderItemDto> items;

  public BigDecimal getTotalQuantity() {
    return items.stream()
        .map(OrderItemDto::getQuantity)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public BigDecimal getTotalAmount() {
    return items.stream()
        .map(OrderItemDto::getAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
