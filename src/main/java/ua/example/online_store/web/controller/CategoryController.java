package ua.example.online_store.web.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;
import ua.example.online_store.service.CategoryService;
import ua.example.online_store.service.ProductService;
import ua.example.online_store.web.dto.CategoryDto;
import ua.example.online_store.web.mapper.CategoryMapper;
import ua.example.online_store.web.mapper.PriceMapper;

@Slf4j
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class CategoryController {

  public static final String INVOKED_METHOD = "invoked method {}";
  private final CategoryService categoryService;
  private final ProductService productService;
  private final CategoryMapper categoryMapper;
  private final PriceMapper priceMapper;

  @GetMapping
  public ResponseEntity<List<CategoryDto>> getAll() {
    log.info(INVOKED_METHOD, "getAll()");
    List<CategoryDto> categoryDtoList = categoryMapper.toDto(categoryService.getAll());
    categoryDtoList.forEach(categoryDto -> {
          categoryDto.setProductCount(
              productService.productCountInCategory(categoryDto.getId()));
          categoryDto.setMinPrice(
              priceMapper.toDto(productService.minPriceInCategory(categoryDto.getId())));
        }
    );
    return ResponseEntity.ok(categoryDtoList);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id) {
    log.info(INVOKED_METHOD, "getCategory()");
    CategoryDto categoryDto = categoryMapper.toDto(categoryService.findById(id)
        .orElseThrow(() -> new NotFoundException("Category not found")));
    categoryDto.setProductCount(productService.productCountInCategory(id));
    categoryDto.setMinPrice(priceMapper.toDto(productService.minPriceInCategory(id)));
    return ResponseEntity.ok(categoryDto);
  }

  @PostMapping
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public ResponseEntity<CategoryDto> addCategory(
      @Valid @RequestBody CategoryDto categoryDto) {
    log.info(INVOKED_METHOD, "addCategory()");
    return ResponseEntity.ok(categoryMapper.toDto(
        categoryService.addCategory(categoryDto)));
  }

}
