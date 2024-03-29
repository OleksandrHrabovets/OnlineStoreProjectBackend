package ua.example.online_store.web.dto;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SKUDto {

  private Long id;
  @Valid
  private List<SKUCharacteristicDto> characteristics;
  private boolean status;
  private BigDecimal availableQuantity;

}
