package ua.example.online_store.web.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import ua.example.online_store.util.DefaultValues;

@Data
@Builder
public class OrderItemDto {

  private Long id;
  private SKUDto sku;
  private BigDecimal price;
  private BigDecimal quantity;
  private BigDecimal amount;

  public String getCurrencyCode() {
    return DefaultValues.DEFAULT_CURRENCY_CODE;
  }

}
