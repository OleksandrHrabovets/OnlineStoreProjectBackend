package ua.example.online_store.repository.subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.example.online_store.model.subscription.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

}
