package ua.example.online_store.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.example.online_store.service.CharacteristicService;
import ua.example.online_store.web.dto.CharacteristicDto;
import ua.example.online_store.web.mapper.CharacteristicMapper;

@Slf4j
@RestController
@RequestMapping("/api/v1/characteristics")
@RequiredArgsConstructor
public class CharacteristicController {

  private final CharacteristicService characteristicService;
  private final CharacteristicMapper characteristicMapper;

  @GetMapping
  public ResponseEntity<List<CharacteristicDto>> getAll() {
    log.info("invoked method {}", "getAll()");
    return ResponseEntity.ok(characteristicMapper.toDto(characteristicService.getAll()));
  }

}
