package ua.example.online_store.repository.subscription;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.example.online_store.model.subscription.Subscriber;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

  List<Subscriber> findAllBySubscriptionId(Long subscriptionId);

  Optional<Subscriber> findAllBySubscriptionIdAndEmail(Long subscriptionId, String email);

}
