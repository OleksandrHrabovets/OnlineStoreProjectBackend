package ua.example.online_store.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.example.online_store.model.Category;
import ua.example.online_store.model.Price;
import ua.example.online_store.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

  @EntityGraph(attributePaths = {"category", "price.currency", "photos"},
      type = EntityGraphType.LOAD)
  Page<Product> findAll(Specification<Product> specification, Pageable pageable);

  @Override
  @EntityGraph(attributePaths = {"category", "price.currency", "photos"},
      type = EntityGraphType.LOAD)
  Optional<Product> findById(Long id);

  Long countByCategory(Category category);

  @Query("SELECT p.price FROM Product AS p "
      + "WHERE p.category.id = ?1 "
      + "ORDER BY p.price.value ASC")
  List<Price> minPriceInCategory(Long categoryId, Pageable pageable);

  Optional<Product> findByTitle(String title);

  List<Product> findAllByTitle(String title);
}
