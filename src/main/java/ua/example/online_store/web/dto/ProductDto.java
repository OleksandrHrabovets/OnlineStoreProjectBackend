package ua.example.online_store.web.dto;

import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

  private Long id;
  private String title;
  private String description;
  private PriceDto price;
  private CategoryDto category;
  private boolean status;
  private Set<SKUDto> skuSet;

}
