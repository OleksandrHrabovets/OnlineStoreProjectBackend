package ua.example.online_store.service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.example.online_store.web.dto.CategoryDto;
import ua.example.online_store.web.dto.CurrencyDto;
import ua.example.online_store.web.dto.PriceDto;
import ua.example.online_store.web.dto.ProductDto;
import ua.example.online_store.web.mapper.CategoryMapper;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final CategoryService categoryService;
  private final CategoryMapper categoryMapper;
  private final List<ProductDto> products = new ArrayList<>();


  @PostConstruct
  private void init() {

    CurrencyDto currencyUAH = CurrencyDto.builder()
        .id(1L)
        .code("UAH")
        .title("Гривня")
        .build();

    List<CategoryDto> categoriesDto = categoryMapper.toDto(categoryService.getAll());
    CategoryDto category1 = categoriesDto.get(0);
    CategoryDto category2 = categoriesDto.get(1);

    products.add(
        ProductDto.builder()
            .id(1L)
            .title("Футболка жіноча Nike")
            .description("Футболка жіноча Nike ... повний опис")
            .price(PriceDto.builder()
                .currency(currencyUAH)
                .value(500)
                .build())
            .category(category1)
            .skuSet(new HashSet<>())
            .status(true)
            .build()
    );
    products.add(
        ProductDto.builder()
            .id(2L)
            .title("Спортивний костюм жіночий Nike")
            .description("Спортивний костюм жіночий Nike ... повний опис")
            .price(PriceDto.builder()
                .currency(currencyUAH)
                .value(2500)
                .build())
            .category(category2)
            .skuSet(new HashSet<>())
            .status(true)
            .build());
  }

  public List<ProductDto> getAll() {
    return products;
  }
}
