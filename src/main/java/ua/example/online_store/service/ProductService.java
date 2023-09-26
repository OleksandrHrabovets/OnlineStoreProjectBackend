package ua.example.online_store.service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import ua.example.online_store.web.dto.ProductDto;

@Service
public class ProductService {

  List<ProductDto> products = new ArrayList<>();

  @PostConstruct
  private void init() {
    products.add(
        ProductDto.builder()
            .id(1L)
            .title("product 1")
            .description("description 1")
            .price(100)
            .build());
    products.add(
        ProductDto.builder()
            .id(2L)
            .title("product 2")
            .description("description 2")
            .price(200)
            .build());

  }

  public List<ProductDto> getAll() {
    return products;
  }
}
