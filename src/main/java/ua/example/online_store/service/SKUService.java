package ua.example.online_store.service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.example.online_store.web.dto.CharacteristicDto;
import ua.example.online_store.web.dto.ProductDto;
import ua.example.online_store.web.dto.SKUCharacteristicDto;
import ua.example.online_store.web.dto.SKUDto;

@Service
@RequiredArgsConstructor
public class SKUService {

  private final ProductService productService;
  private final CharacteristicService characteristicService;

  private final List<SKUDto> skuDtoList = new ArrayList<>();

  @PostConstruct
  private void init() {

    List<ProductDto> products = productService.getAll();
    ProductDto product1 = products.get(0);
    ProductDto product2 = products.get(1);

    List<CharacteristicDto> characteristics = characteristicService.getAll();
    CharacteristicDto size = characteristics.get(0);
    CharacteristicDto color = characteristics.get(1);

    SKUDto sku1 = SKUDto.builder()
        .product(product1)
        .characteristics(
            Set.of(
                SKUCharacteristicDto.builder()
                    .characteristic(size)
                    .value("46")
                    .build(),
                SKUCharacteristicDto.builder()
                    .characteristic(color)
                    .value("червоний")
                    .build()
            )
        )
        .status(true)
        .build();
    SKUDto sk2 = SKUDto.builder()
        .product(product2)
        .characteristics(
            Set.of(
                SKUCharacteristicDto.builder()
                    .characteristic(size)
                    .value("48")
                    .build(),
                SKUCharacteristicDto.builder()
                    .characteristic(color)
                    .value("зелений")
                    .build()
            )
        )
        .status(true)
        .build();

    skuDtoList.add(sku1);
    skuDtoList.add(sk2);

  }

  public List<SKUDto> getAll() {
    return skuDtoList;
  }
}
