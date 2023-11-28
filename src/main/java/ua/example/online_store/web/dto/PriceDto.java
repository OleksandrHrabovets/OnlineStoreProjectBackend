package ua.example.online_store.web.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceDto {

  @NotNull
  @Min(0)
  @Digits(integer = 12, fraction = 0)
  private BigDecimal value;
  @NotNull
  private CurrencyDto currency;

}
