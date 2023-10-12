package ua.example.online_store.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

  private Long id;
  @NotBlank
  private String title;
  @NotBlank
  private String description;
  @NotNull
  @Valid
  private PriceDto price;
  @NotNull
  @Valid
  private CategoryDto category;
  private boolean status;
  @NotNull
  @Valid
  private List<SKUDto> skuSet;
  @Valid
  private List<PhotoDto> photos;

}
