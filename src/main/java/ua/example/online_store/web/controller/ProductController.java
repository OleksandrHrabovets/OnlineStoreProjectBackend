package ua.example.online_store.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.example.online_store.service.ProductService;
import ua.example.online_store.web.dto.ProductDto;
import ua.example.online_store.web.mapper.ProductMapper;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;
  private final ProductMapper productMapper;

  @GetMapping
  public ResponseEntity<List<ProductDto>> getAll() {
    log.info("invoked method {}", "getAll()");
    return ResponseEntity.ok(productMapper.toDto(productService.getAll()));
  }

}
