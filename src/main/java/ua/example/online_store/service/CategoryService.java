package ua.example.online_store.service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.example.online_store.model.Category;
import ua.example.online_store.repository.CategoryRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  @PostConstruct
  private void init() {

    if (categoryRepository.findAll().isEmpty()) {
      List<Category> categories = new ArrayList<>();
      categories.add(
          Category.builder()
              .id(1L)
              .title("Футболки")
              .build());
      categories.add(
          Category.builder()
              .id(2L)
              .title("Спортивні костюми")
              .build());
      categoryRepository.saveAll(categories);
    }

  }

  public List<Category> getAll() {
    log.info("invoked method {}", "getAll()");
    return categoryRepository.findAll();
  }
}
