package ua.example.online_store.service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.example.online_store.model.Characteristic;
import ua.example.online_store.model.Product;
import ua.example.online_store.model.SKU;
import ua.example.online_store.model.SKUCharacteristic;
import ua.example.online_store.repository.SKUCharacteristicRepository;
import ua.example.online_store.repository.SKURepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class SKUService {

  private final ProductService productService;
  private final CharacteristicService characteristicService;
  private final SKURepository skuRepository;
  private final SKUCharacteristicRepository characteristicRepository;


  private final List<SKU> skuList = new ArrayList<>();

  @PostConstruct
  private void init() {

    if (skuRepository.findAll().isEmpty()) {
      List<Product> products = productService.getAll(null, "", null).toList();
      Product product1 = products.get(0);
      Product product2 = products.get(1);

      List<Characteristic> characteristics = characteristicService.getAll();
      Characteristic size = characteristics.get(0);
      Characteristic color = characteristics.get(1);

      SKU sku1 = SKU.builder()
          .product(product1)
          .status(true)
          .build();
      SKU sku2 = SKU.builder()
          .product(product2)
          .status(true)
          .build();

      product1.setSkuSet(List.of(sku1));
      product2.setSkuSet(List.of(sku2));
      skuList.add(sku1);
      skuList.add(sku2);
      skuRepository.saveAll(skuList);

      SKUCharacteristic size46 = SKUCharacteristic.builder()
          .sku(sku1)
          .characteristic(size)
          .value("46")
          .build();
      SKUCharacteristic colorRed = SKUCharacteristic.builder()
          .sku(sku1)
          .characteristic(color)
          .value("червоний")
          .build();
      SKUCharacteristic size48 = SKUCharacteristic.builder()
          .sku(sku2)
          .characteristic(size)
          .value("48")
          .build();
      SKUCharacteristic colorGreen = SKUCharacteristic.builder()
          .sku(sku2)
          .characteristic(color)
          .value("зелений")
          .build();
      characteristicRepository.saveAll(List.of(size46, size48, colorRed, colorGreen));
    }

  }

  public List<SKU> getAll() {
    log.info("invoked method {}", "getAll()");
    return skuRepository.findAll();
  }

  public Optional<SKU> findById(Long id) {
    return skuRepository.findById(id);
  }

}
