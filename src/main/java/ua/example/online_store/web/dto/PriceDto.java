package ua.example.online_store.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceDto {

  @NotNull
  @Min(0)
  private double value;
  @NotNull
  private CurrencyDto currency;

}
