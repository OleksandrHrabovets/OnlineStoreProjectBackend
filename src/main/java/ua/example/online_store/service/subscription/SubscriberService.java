package ua.example.online_store.service.subscription;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.example.online_store.model.subscription.Subscriber;
import ua.example.online_store.repository.subscription.SubscriberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriberService {

  private final SubscriberRepository subscriberRepository;

  public List<Subscriber> findAllBySubscriptionId(Long id) {
    return subscriberRepository.findAllBySubscriptionId(id);
  }

  public Optional<Subscriber> findAllBySubscriptionIdAndEmail(Long id, String email) {
    return subscriberRepository.findAllBySubscriptionIdAndEmail(id, email);
  }

  public Subscriber save(Subscriber subscriber) {
    return subscriberRepository.save(subscriber);
  }

  public void delete(Subscriber subscriber) {
    subscriberRepository.delete(subscriber);
  }

}
