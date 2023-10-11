package ua.example.online_store.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.example.online_store.model.SKU;
import ua.example.online_store.repository.SKURepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class SKUService {

  public static final String INVOKED_METHOD = "invoked method {}";
  private final SKURepository skuRepository;

  public List<SKU> getAll() {
    log.info(INVOKED_METHOD, "getAll()");
    return skuRepository.findAll();
  }

  public Optional<SKU> findById(Long id) {
    return skuRepository.findById(id);
  }

  public List<SKU> findAllByStatus(boolean status) {
    log.info(INVOKED_METHOD, "getAll()");
    return skuRepository.findAllByStatus(status);
  }

  public List<SKU> saveAll(List<SKU> skuSet) {
    log.info(INVOKED_METHOD, "saveAll()");
    return skuRepository.saveAll(skuSet);
  }
}
