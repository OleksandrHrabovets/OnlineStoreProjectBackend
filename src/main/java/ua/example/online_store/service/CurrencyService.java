package ua.example.online_store.service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import ua.example.online_store.web.dto.CurrencyDto;

@Service
public class CurrencyService {

  private final List<CurrencyDto> currencies = new ArrayList<>();

  @PostConstruct
  private void init() {

    currencies.add(
        CurrencyDto.builder()
            .id(1L)
            .code("UAH")
            .title("Гривня")
            .build());

  }

  public List<CurrencyDto> getAll() {
    return currencies;
  }
}
