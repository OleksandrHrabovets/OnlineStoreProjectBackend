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
  @NotBlank
  private String name;

  public String getValue() {
    if (characteristic.getTitle().equals("color")) {
      if (name != null) {
        return Color.valueOf(name).getValue();
      } else {
        return Color.valueOf(value).getValue();
      }
    } else if (characteristic.getTitle().equals("size")) {
      if (name != null) {
        return Size.valueOf(name).getValue();
      } else {
        return Size.valueOf(value).getValue();
      }
    }
    return value;
  }

  public String getName() {
    if (characteristic.getTitle().equals("color")) {
      if (name != null) {
        return Color.valueOf(name).name();
      } else {
        return Color.valueOf(value).name();
      }
    } else if (characteristic.getTitle().equals("size")) {
      if (name != null) {
        return Size.valueOf(name).name();
      } else {
        return Size.valueOf(value).name();
      }
    }
    return value;
  }
}
