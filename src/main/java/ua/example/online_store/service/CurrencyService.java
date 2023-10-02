package ua.example.online_store.service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.example.online_store.model.Currency;
import ua.example.online_store.repository.CurrencyRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {

  private final CurrencyRepository currencyRepository;

  @PostConstruct
  private void init() {

    if (currencyRepository.findAll().isEmpty()) {
      List<Currency> currencies = new ArrayList<>();
      currencies.add(
          Currency.builder()
              .id(1L)
              .code("UAH")
              .title("Гривня")
              .build());
      currencyRepository.saveAll(currencies);
    }
  }

  public List<Currency> getAll() {
    log.info("invoked method {}", "getAll()");
    return currencyRepository.findAll();
  }
}
