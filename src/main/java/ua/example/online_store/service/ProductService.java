package ua.example.online_store.service;

import static org.springframework.data.jpa.domain.Specification.where;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ua.example.online_store.model.Category;
import ua.example.online_store.model.Currency;
import ua.example.online_store.model.Price;
import ua.example.online_store.model.Product;
import ua.example.online_store.repository.PriceRepository;
import ua.example.online_store.repository.ProductRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final PriceRepository priceRepository;
  private final CategoryService categoryService;

  @PostConstruct
  private void init() {

    if (productRepository.findAll().isEmpty()) {
      List<Product> products = new ArrayList<>();
      Currency currencyUAH = Currency.builder()
          .id(1L)
          .code("UAH")
          .title("Гривня")
          .build();

      List<Category> categories = categoryService.getAll();
      Category category1 = categories.get(0);
      Category category2 = categories.get(1);

      Price price1 = Price.builder()
          .currency(currencyUAH)
          .value(500)
          .build();
      Price price2 = Price.builder()
          .currency(currencyUAH)
          .value(2500)
          .build();
      priceRepository.saveAll(List.of(price1, price2));

      products.add(
          Product.builder()
              .id(1L)
              .title("Футболка жіноча Nike")
              .description("Футболка жіноча Nike ... повний опис")
              .price(price1)
              .category(category1)
              .status(true)
              .build()
      );
      products.add(
          Product.builder()
              .id(2L)
              .title("Спортивний костюм жіночий Nike")
              .description("Спортивний костюм жіночий Nike ... повний опис")
              .price(price2)
              .category(category2)
              .status(true)
              .build());
      productRepository.saveAll(products);
    }

  }

  public Page<Product> getAll(Pageable pageable, String title, Long categoryId) {
    log.info("invoked method {}", "getAll()");
    Specification<Product> specification = null;
    if (title != null && !title.isEmpty()) {
      specification = where(titleSpecification(title)).and(specification);
    }
    if (categoryId != null) {
      Category category = categoryService.findById(categoryId).orElseThrow();
      specification = where(categorySpecification(category)).and(specification);
    }
    return productRepository.findAll(specification, pageable);
  }

  private Specification<Product> titleSpecification(String title) {
    return (root, query, criteriaBuilder)
        -> criteriaBuilder.like(criteriaBuilder.lower(root.
            get("title")),
        "%" + title.toLowerCase() + "%");
  }

  private Specification<Product> categorySpecification(Category category) {
    return (root, query, criteriaBuilder)
        -> criteriaBuilder.equal(root.get("category"), category);
  }
}
