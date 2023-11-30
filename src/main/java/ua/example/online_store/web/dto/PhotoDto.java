package ua.example.online_store.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import ua.example.online_store.model.enums.Color;

@Data
@Builder
public class PhotoDto {

  private Long id;
  private Color color;
  private String colorValue;
  @NotBlank
  private String url;

  public String getColorValue() {
    if (color == null) {
      return "";
    }
    return color.getValue();
  }
}
