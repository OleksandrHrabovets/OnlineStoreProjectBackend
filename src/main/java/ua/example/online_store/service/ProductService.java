package ua.example.online_store.service;

import static org.springframework.data.jpa.domain.Specification.where;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ua.example.online_store.model.Category;
import ua.example.online_store.model.Photo;
import ua.example.online_store.model.Price;
import ua.example.online_store.model.Product;
import ua.example.online_store.model.SKU;
import ua.example.online_store.model.SKUCharacteristic;
import ua.example.online_store.model.enums.Color;
import ua.example.online_store.model.enums.Size;
import ua.example.online_store.repository.ProductRepository;
import ua.example.online_store.web.dto.ProductDto;
import ua.example.online_store.web.mapper.PhotoMapper;
import ua.example.online_store.web.mapper.SKUMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

  public static final String INVOKED_METHOD = "invoked method {}";
  private final ProductRepository productRepository;
  private final CategoryService categoryService;
  private final PriceService priceService;
  private final SKUService skuService;
  private final SKUCharacteristicService skuCharacteristicService;
  private final SKUMapper skuMapper;
  private final PhotoMapper photoMapper;
  private final PhotoService photoService;

  @Value("${app.product.price-delta-for-similar-products}")
  private double priceDelta;

  public Optional<Product> getProduct(Long id) {
    log.info(INVOKED_METHOD, "getProduct()");
    return productRepository.findById(id);
  }

  public Long productCountInCategory(Long categoryId) {
    Category category = categoryService.findById(categoryId).orElseThrow();
    return productRepository.countByCategory(category);
  }

  public Price minPriceInCategory(Long categoryId) {
    return productRepository.minPriceInCategory(categoryId, PageRequest.of(0, 1))
        .stream().findFirst()
        .orElse(Price.builder().build());
  }

  public Page<Product> getAll(Pageable pageable, String title, Long categoryId) {
    log.info(INVOKED_METHOD, "getAll()");
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

  private Specification<Product> notEqualIdSpecification(Long id) {
    return (root, query, criteriaBuilder)
        -> criteriaBuilder.notEqual(root.get("id"), id);
  }

  private Specification<Product> priceGreaterThanSpecification(double price) {
    return (root, query, criteriaBuilder)
        -> criteriaBuilder.greaterThan(root.get("price").get("value"), price);
  }

  private Specification<Product> priceLessThanSpecification(double price) {
    return (root, query, criteriaBuilder)
        -> criteriaBuilder.lessThan(root.get("price").get("value"), price);
  }

  public Page<Product> getSimilarProducts(Pageable pageable, Long id) {
    log.info(INVOKED_METHOD, "getAll()");
    Specification<Product> specification = null;

    Product product = productRepository.findById(id).orElseThrow();
    specification = where(categorySpecification(product.getCategory())).and(specification);
    specification = where(notEqualIdSpecification(id)).and(specification);
    specification = where(priceGreaterThanSpecification(product.getPrice().getValue() - priceDelta))
        .and(specification);
    specification = where(priceLessThanSpecification(product.getPrice().getValue() + priceDelta))
        .and(specification);
    return productRepository.findAll(specification, pageable);

  }

  public Optional<Product> findById(Long id) {
    return productRepository.findById(id);
  }

  @Transactional
  public Product addProduct(ProductDto productDto) {
    log.info(INVOKED_METHOD, "addProduct()");
    Category category = categoryService.findById(productDto.getCategory().getId())
        .orElseThrow(() -> new NotFoundException("category not found"));
    Product product = productRepository.findById(productDto.getId())
        .orElse(Product.builder()
            .id(null)
            .build());
    product.setCategory(category);
    product.setTitle(productDto.getTitle());
    product.setDescription(productDto.getDescription());
    product.setPrice(priceService.addPrice(productDto.getPrice()));
    product.setStatus(true);
    Product saveProduct = productRepository.save(product);

    List<SKU> skuSet = productDto.getSkuSet().stream().map(skuMapper::toEntity).toList();
    skuSet.forEach(sku -> {
      sku.setId(null);
      sku.setProduct(saveProduct);
      sku.getCharacteristics().forEach(skuCharacteristic -> {
        skuCharacteristic.setValue(mapValueOfSkuCharacteristic(skuCharacteristic));
        skuCharacteristic.setId(null);
        skuCharacteristic.setSku(sku);
      });
      skuCharacteristicService.saveAll(sku.getCharacteristics());
    });
    skuService.saveAll(skuSet);

    List<Photo> photos = productDto.getPhotos().stream().map(photoMapper::toEntity).toList();
    photos.forEach(photo -> {
      photo.setId(null);
      photo.setProduct(saveProduct);
    });
    photoService.saveAll(photos);

    return saveProduct;
  }

  public String mapValueOfSkuCharacteristic(SKUCharacteristic skuCharacteristic) {
    if (skuCharacteristic.getId() == 2L) {
      return Color.getColor(skuCharacteristic.getValue()).name();
    } else if (skuCharacteristic.getId() == 1L) {
      return Size.getSize(skuCharacteristic.getValue()).name();
    } else {
      throw new IllegalArgumentException(
          "Can not map value of skuCharacteristic %s".formatted(skuCharacteristic.getValue()));
    }
  }

}
