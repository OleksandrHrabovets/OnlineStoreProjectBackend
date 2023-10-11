package ua.example.online_store.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.example.online_store.model.SKUCharacteristic;
import ua.example.online_store.repository.SKUCharacteristicRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class SKUCharacteristicService {

  public static final String INVOKED_METHOD = "invoked method {}";
  private final SKUCharacteristicRepository skuCharacteristicRepository;

  public List<SKUCharacteristic> getAll() {
    log.info(INVOKED_METHOD, "getAll()");
    return skuCharacteristicRepository.findAll();
  }

  public Optional<SKUCharacteristic> findById(Long id) {
    return skuCharacteristicRepository.findById(id);
  }

  public List<SKUCharacteristic> saveAll(List<SKUCharacteristic> skuCharacteristics) {
    log.info(INVOKED_METHOD, "saveAll()");
    return skuCharacteristicRepository.saveAll(skuCharacteristics);
  }
}
