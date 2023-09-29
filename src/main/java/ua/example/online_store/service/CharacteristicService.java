package ua.example.online_store.service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import ua.example.online_store.web.dto.CharacteristicDto;

@Service
public class CharacteristicService {

  private final List<CharacteristicDto> characteristics = new ArrayList<>();

  @PostConstruct
  private void init() {

    characteristics.add(
        CharacteristicDto.builder()
            .id(1L)
            .title("size")
            .build());
    characteristics.add(
        CharacteristicDto.builder()
            .id(2L)
            .title("color")
            .build());

  }

  public List<CharacteristicDto> getAll() {
    return characteristics;
  }
}
