package ua.example.online_store.service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.example.online_store.model.Category;
import ua.example.online_store.repository.CategoryRepository;
import ua.example.online_store.web.dto.CategoryDto;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  private final List<CategoryDto> categories = new ArrayList<>();

  @PostConstruct
  private void init() {

    categories.add(
        CategoryDto.builder()
            .id(1L)
            .title("Футболки")
            .build());
    categories.add(
        CategoryDto.builder()
            .id(2L)
            .title("Спортивні костюми")
            .build());
    categoryRepository.saveAll(categories.stream().map(categoryDto -> Category.builder()
        .title(categoryDto.getTitle())
        .build()).toList());
  }

  public List<Category> getAll() {
    return categoryRepository.findAll();
  }
}
