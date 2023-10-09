package ua.example.online_store.web.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartAddItemDto {

  private String sessionId;
  private Long skuId;
  private BigDecimal price;
  private BigDecimal quantity;
  private BigDecimal amount;
}
