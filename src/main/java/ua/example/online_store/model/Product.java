package ua.example.online_store.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

  private Long id;
  private String title;
  private String description;
  private double price;

}
