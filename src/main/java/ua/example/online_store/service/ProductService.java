package ua.example.online_store.service;

import static org.springframework.data.jpa.domain.Specification.where;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ua.example.online_store.model.Category;
import ua.example.online_store.model.Photo;
import ua.example.online_store.model.Product;
import ua.example.online_store.model.SKU;
import ua.example.online_store.repository.PriceRepository;
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
  private final PriceRepository priceRepository;
  private final CategoryService categoryService;
  private final PriceService priceService;
  private final SKUService skuService;
  private final SKUCharacteristicService skuCharacteristicService;
  private final SKUMapper skuMapper;
  private final PhotoMapper photoMapper;
  private final PhotoService photoService;

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


  public Page<Product> getSimilarProducts(Pageable pageable, Long id) {
    log.info(INVOKED_METHOD, "getAll()");
    Specification<Product> specification = null;

    Product product = productRepository.findById(id).orElseThrow();
    specification = where(categorySpecification(product.getCategory())).and(specification);
    specification = where(notEqualIdSpecification(id)).and(specification);
    specification = where(priceGreaterThanSpecification(product.getPrice().getValue())).and(
        specification);
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

}
