package ua.example.online_store.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.example.online_store.service.CurrencyService;
import ua.example.online_store.web.dto.CurrencyDto;
import ua.example.online_store.web.mapper.CurrencyMapper;

@Slf4j
@RestController
@RequestMapping("/api/v1/currencies")
@RequiredArgsConstructor
public class CurrencyController {

  private final CurrencyService currencyService;
  private final CurrencyMapper currencyMapper;

  @GetMapping
  public ResponseEntity<List<CurrencyDto>> getAll() {
    log.info("invoked method {}", "getAll()");
    return ResponseEntity.ok(currencyMapper.toDto(currencyService.getAll()));
  }

}
