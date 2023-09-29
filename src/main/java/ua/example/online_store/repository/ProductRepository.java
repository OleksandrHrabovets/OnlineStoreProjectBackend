package ua.example.online_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.example.online_store.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
