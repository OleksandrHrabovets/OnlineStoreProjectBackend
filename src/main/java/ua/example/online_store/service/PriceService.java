package ua.example.online_store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ua.example.online_store.model.Price;
import ua.example.online_store.repository.PriceRepository;
import ua.example.online_store.web.dto.PriceDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceService {

  public static final String INVOKED_METHOD = "invoked method {}";
  private final PriceRepository priceRepository;
  private final CurrencyService currencyService;

  public Price addPrice(PriceDto priceDto) {
    log.info(INVOKED_METHOD, "addPrice()");
    Price price = Price.builder()
        .id(null)
        .currency(currencyService.getAll().stream()
            .filter(currency -> priceDto.getCurrency().getId().equals(currency.getId()))
            .findFirst()
            .orElseThrow(() -> new NotFoundException("currency not found")))
        .value(priceDto.getValue())
        .build();
    return priceRepository.save(price);
  }
}
