package ua.example.online_store.web.dto;

import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SKUDto {

  private ProductDto product;
  private Set<SKUCharacteristicDto> characteristics;
  private boolean status;

}
