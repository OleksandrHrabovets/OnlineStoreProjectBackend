package ua.example.online_store.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ua.example.online_store.model.AvailableQuantity;
import ua.example.online_store.model.SKU;
import ua.example.online_store.repository.AvailableQuantityRepository;
import ua.example.online_store.web.dto.AvailableQuantityDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class AvailableQuantityService {

  private final AvailableQuantityRepository availableQuantityRepository;
  private final SKUService skuService;

  public List<AvailableQuantity> getAll() {

    log.info("invoked method {}", "getAll()");
    return availableQuantityRepository.findAll();

  }

  public Optional<AvailableQuantity> findById(Long id) {

    return availableQuantityRepository.findById(id);

  }

  public Optional<AvailableQuantity> findBySkuId(Long skuId) {

    return availableQuantityRepository.findBySkuId(skuId);

  }

  @Transactional
  public AvailableQuantity setAvailableQuantity(AvailableQuantityDto dto) {

    SKU sku = skuService.findById(dto.getSku().getId())
        .orElseThrow(() -> new NotFoundException("sku not found"));
    AvailableQuantity availableQuantity = availableQuantityRepository.findBySkuId(
            dto.getSku().getId())
        .orElse(AvailableQuantity.builder()
            .sku(sku)
            .build());
    availableQuantity.setQuantity(dto.getQuantity());
    return availableQuantityRepository.save(availableQuantity);

  }

}
