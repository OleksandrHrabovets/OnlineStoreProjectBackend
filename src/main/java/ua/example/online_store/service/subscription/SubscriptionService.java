package ua.example.online_store.service.subscription;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.example.online_store.model.subscription.Subscription;
import ua.example.online_store.repository.subscription.SubscriptionRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {

  private final SubscriptionRepository subscriptionRepository;

  @PostConstruct
  private void init() {

    if (subscriptionRepository.findAll().isEmpty()) {
      List<Subscription> subscriptions = new ArrayList<>();
      subscriptions.add(
          Subscription.builder()
              .id(1L)
              .title("New products")
              .build());
      subscriptions.add(
          Subscription.builder()
              .id(2L)
              .title("Discounts")
              .build());
      subscriptionRepository.saveAll(subscriptions);
    }

  }

  public List<Subscription> getAll() {
    log.info("invoked method {}", "getAll()");
    return subscriptionRepository.findAll();
  }

  public Optional<Subscription> findById(Long id) {
    return subscriptionRepository.findById(id);
  }
}
