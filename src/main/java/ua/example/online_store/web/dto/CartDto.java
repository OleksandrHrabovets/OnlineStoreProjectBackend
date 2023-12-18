package ua.example.online_store.web.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import ua.example.online_store.util.DefaultValues;

@Data
@Builder
public class CartDto {

  private Long id;
  private String sessionId;
  private BigDecimal totalQuantity;
  private BigDecimal totalAmount;
  private String currencyCode;
  private List<CartItemDto> items;

  public BigDecimal getTotalQuantity() {
    return items.stream()
        .map(CartItemDto::getQuantity)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public BigDecimal getTotalAmount() {
    return items.stream()
        .map(CartItemDto::getAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public String getCurrencyCode() {
    return DefaultValues.DEFAULT_CURRENCY_CODE;
  }

}
