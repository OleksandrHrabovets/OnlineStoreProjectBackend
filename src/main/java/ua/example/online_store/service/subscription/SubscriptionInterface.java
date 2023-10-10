package ua.example.online_store.service.subscription;

public interface SubscriptionInterface {

  void subscribe(String email);

  void unsubscribe(String email);

  void notifySubscribers();

}
