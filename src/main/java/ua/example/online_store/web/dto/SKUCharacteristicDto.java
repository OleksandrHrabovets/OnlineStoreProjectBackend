package ua.example.online_store.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import ua.example.online_store.model.enums.Size;

@Data
@Builder
public class SKUCharacteristicDto {

  private Long id;
  @Valid
  private CharacteristicDto characteristic;
  @NotBlank
  private String value;

  public String getValue() {
    if (characteristic.getId() == 2L) {
      return value;
    } else if (characteristic.getId() == 1L) {
      return Size.valueOf(value).getValue();
    }
    return value;
  }
}
