package ua.example.online_store.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SKUCharacteristicDto {

  private Long id;
  @Valid
  private CharacteristicDto characteristic;
  @NotBlank
  private String value;

}
