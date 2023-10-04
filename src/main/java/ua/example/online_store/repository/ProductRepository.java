package ua.example.online_store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.example.online_store.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Page<Product> findAll(Specification<Product> specification, Pageable pageable);

}
