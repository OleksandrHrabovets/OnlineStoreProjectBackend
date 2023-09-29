package ua.example.online_store.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.example.online_store.service.CurrencyService;
import ua.example.online_store.web.dto.CurrencyDto;

@RestController
@RequestMapping("/api/v1/currencies")
@RequiredArgsConstructor
public class CurrencyController {

  private final CurrencyService currencyService;

  @GetMapping
  public ResponseEntity<List<CurrencyDto>> getAll() {
    return ResponseEntity.ok(currencyService.getAll());
  }

}
