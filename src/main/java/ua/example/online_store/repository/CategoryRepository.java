package ua.example.online_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.example.online_store.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
