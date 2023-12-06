package ua.example.online_store.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvailableQuantityDto {

  private Long id;
  @NotNull
  private SKUDto sku;
  @Min(0)
  @Max(1000)
  private BigDecimal quantity;

}
