package ua.example.online_store.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceDto {

  private double value;
  private CurrencyDto currency;

}
