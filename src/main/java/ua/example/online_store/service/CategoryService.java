package ua.example.online_store.service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.example.online_store.model.Category;
import ua.example.online_store.repository.CategoryRepository;
import ua.example.online_store.web.dto.CategoryDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

  public static final String INVOKED_METHOD = "invoked method {}";
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
    log.info(INVOKED_METHOD, "getAll()");
    return categoryRepository.findAll();
  }

  public Optional<Category> findById(Long id) {
    return categoryRepository.findById(id);
  }

  public Category addCategory(CategoryDto categoryDto) {
    log.info(INVOKED_METHOD, "addCategory()");
    Category category = categoryRepository.findById(categoryDto.getId())
        .orElse(Category.builder()
            .build());
    category.setTitle(categoryDto.getTitle());
    return categoryRepository.save(category);
  }
}
