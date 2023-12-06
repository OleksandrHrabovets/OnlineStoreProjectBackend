package ua.example.online_store.web.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemDto {

  private Long id;
  @NotNull
  private SKUDto sku;
  @NotNull
  private BigDecimal price;
  @NotNull
  private BigDecimal quantity;
  @NotNull
  private BigDecimal amount;

}
