package ua.example.online_store.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvailableQuantityDto {

  private Long id;
  private SKUDto sku;
  @Min(0)
  @Max(1000)
  private BigDecimal quantity;

}
