package ua.example.online_store.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SKUCharacteristicDto {

  private Long id;
  private SKUDto sku;
  private CharacteristicDto characteristic;
  private String value;

}
