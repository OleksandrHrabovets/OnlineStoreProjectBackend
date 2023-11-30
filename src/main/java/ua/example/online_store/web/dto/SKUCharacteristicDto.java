package ua.example.online_store.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import ua.example.online_store.model.enums.Color;
import ua.example.online_store.model.enums.Size;

@Data
@Builder
public class SKUCharacteristicDto {

  private Long id;
  @Valid
  private CharacteristicDto characteristic;
  @NotBlank
  private String value;

  private String name;

  public String getValue() {
    if (characteristic.getTitle().equals("color")) {
      return Color.valueOf(value).getValue();
    } else if (characteristic.getTitle().equals("size")) {
      return Size.valueOf(value).getValue();
    }
    return value;
  }

  public String getName() {
    if (characteristic.getTitle().equals("color")) {
      return Color.valueOf(value).name();
    } else if (characteristic.getTitle().equals("size")) {
      return Size.valueOf(value).name();
    }
    return value;
  }
}
