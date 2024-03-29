package ua.example.online_store.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {

  private Long id;
  @NotBlank
  private String title;
  private Long productCount;
  private PriceDto minPrice;

}
