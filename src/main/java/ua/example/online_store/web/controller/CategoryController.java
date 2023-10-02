package ua.example.online_store.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.example.online_store.service.CategoryService;
import ua.example.online_store.web.dto.CategoryDto;
import ua.example.online_store.web.mapper.CategoryMapper;

@Slf4j
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;
  private final CategoryMapper categoryMapper;

  @GetMapping
  public ResponseEntity<List<CategoryDto>> getAll() {
    log.info("invoked method {}", "getAll()");
    return ResponseEntity.ok(categoryMapper.toDto(categoryService.getAll()));
  }

}
