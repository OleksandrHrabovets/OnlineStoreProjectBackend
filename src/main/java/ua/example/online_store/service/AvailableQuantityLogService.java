package ua.example.online_store.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.example.online_store.model.AvailableQuantity;
import ua.example.online_store.model.AvailableQuantityLog;
import ua.example.online_store.repository.AvailableQuantityLogRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AvailableQuantityLogService {

  private final AvailableQuantityLogRepository availableQuantityLogRepository;

  public List<AvailableQuantityLog> getAll() {

    log.info("invoked method {}", "getAll()");
    return availableQuantityLogRepository.findAll();

  }

  public Optional<AvailableQuantityLog> findById(Long id) {

    return availableQuantityLogRepository.findById(id);

  }

  public List<AvailableQuantityLog> findBySkuId(Long skuId) {

    return availableQuantityLogRepository.findAllBySkuId(skuId);

  }

  @Transactional
  public AvailableQuantityLog addToLog(AvailableQuantity availableQuantity) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    return availableQuantityLogRepository.save(
        AvailableQuantityLog.builder()
            .userName(username)
            .skuId(availableQuantity.getSku().getId())
            .availableQuantityId(availableQuantity.getId())
            .quantity(availableQuantity.getQuantity())
            .build());

  }

}
