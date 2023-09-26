package ua.example.online_store.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.example.online_store.service.ProductService;
import ua.example.online_store.web.dto.ProductDto;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public ResponseEntity<List<ProductDto>> getAll() {
    return ResponseEntity.ok(productService.getAll());
  }

}
