package ua.example.online_store.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  public ResponseEntity<Page<ProductDto>> getAll(
      @PageableDefault(size = 10, page = 0, sort = "title") Pageable pageable,
      @RequestParam(value = "title", required = false) String title,
      @RequestParam(value = "categoryId", required = false) Long categoryId) {
    log.info("invoked method {}", "getAll()");
    return ResponseEntity.ok(productService.getAll(pageable, title, categoryId)
        .map(productMapper::toDto));
  }

}
