package ua.example.online_store.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyDto {

  private Long id;
  private String code;
  private String title;

}
