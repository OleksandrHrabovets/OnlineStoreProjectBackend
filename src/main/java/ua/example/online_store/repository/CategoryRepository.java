package ua.example.online_store.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.example.online_store.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  Optional<Category> findByTitle(String title);
}
