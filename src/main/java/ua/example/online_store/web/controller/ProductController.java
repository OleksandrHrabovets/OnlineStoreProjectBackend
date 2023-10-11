package ua.example.online_store.web.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@SecurityRequirement(name = "Authorization")
public class ProductController {

  public static final String INVOKED_METHOD = "invoked method {}";
  private final ProductService productService;
  private final ProductMapper productMapper;

  @GetMapping
  public ResponseEntity<Page<ProductDto>> getAll(
      @PageableDefault(size = 10, page = 0, sort = "title") Pageable pageable,
      @RequestParam(value = "title", required = false) String title,
      @RequestParam(value = "categoryId", required = false) Long categoryId) {
    log.info(INVOKED_METHOD, "getAll()");
    return ResponseEntity.ok(productService.getAll(pageable, title, categoryId)
        .map(productMapper::toDto));
  }

  @GetMapping("/similar_products/{id}")
  public ResponseEntity<Page<ProductDto>> getSimilarProducts(
      @PageableDefault(size = 10, page = 0, sort = "price.value",
          direction = Direction.ASC) Pageable pageable,
      @PathVariable Long id) {
    log.info(INVOKED_METHOD, "getSimilarProducts()");
    return ResponseEntity.ok(productService.getSimilarProducts(pageable, id)
        .map(productMapper::toDto));
  }

  @PostMapping
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public ResponseEntity<ProductDto> addProduct(
      @Valid @RequestBody ProductDto productDto) {
    log.info(INVOKED_METHOD, "addProduct()");
    return ResponseEntity.ok(productMapper.toDto(
        productService.addProduct(productDto)));
  }
}
