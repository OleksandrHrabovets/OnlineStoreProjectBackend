package ua.example.online_store.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.example.online_store.model.Category;
import ua.example.online_store.model.Price;
import ua.example.online_store.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Page<Product> findAll(Specification<Product> specification, Pageable pageable);

  Long countByCategory(Category category);

  @Query("SELECT p.price FROM Product AS p "
      + "WHERE p.category.id = ?1 "
      + "ORDER BY p.price.value ASC")
  List<Price> minPriceInCategory(Long categoryId, Pageable pageable);
}
