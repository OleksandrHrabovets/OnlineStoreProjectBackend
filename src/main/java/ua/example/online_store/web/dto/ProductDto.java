package ua.example.online_store.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

  private Long id;
  private String title;
  private String description;
  private double price;

}
