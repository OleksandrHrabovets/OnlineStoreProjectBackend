package ua.example.online_store.web.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SKUDto {

  private Long id;
  private List<SKUCharacteristicDto> characteristics;
  private boolean status;

}
