package ua.example.online_store.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.example.online_store.service.CharacteristicService;
import ua.example.online_store.web.dto.CharacteristicDto;

@RestController
@RequestMapping("/api/v1/characteristics")
@RequiredArgsConstructor
public class CharacteristicController {

  private final CharacteristicService characteristicService;

  @GetMapping
  public ResponseEntity<List<CharacteristicDto>> getAll() {
    return ResponseEntity.ok(characteristicService.getAll());
  }

}
