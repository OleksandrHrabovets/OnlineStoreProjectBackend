package ua.example.online_store.service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.example.online_store.model.Characteristic;
import ua.example.online_store.repository.CharacteristicRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CharacteristicService {

  private final CharacteristicRepository characteristicRepository;

  @PostConstruct
  private void init() {

    if (characteristicRepository.findAll().isEmpty()) {
      List<Characteristic> characteristics = new ArrayList<>();
      characteristics.add(
          Characteristic.builder()
              .id(1L)
              .title("size")
              .build());
      characteristics.add(
          Characteristic.builder()
              .id(2L)
              .title("color")
              .build());
      characteristicRepository.saveAll(characteristics);
    }

  }

  public List<Characteristic> getAll() {
    log.info("invoked method {}", "getAll()");
    return characteristicRepository.findAll();
  }
}
