package ua.example.online_store.service.subscription;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ua.example.online_store.model.SKU;
import ua.example.online_store.model.subscription.Subscriber;
import ua.example.online_store.model.subscription.Subscription;
import ua.example.online_store.service.SKUService;
import ua.example.online_store.service.email.EmailService;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewProductsSubscriptionInterfaceImpl implements SubscriptionInterface {

  public static final long ID = 1L;
  public static final String BASE_TITLE = "Нові товари в нашому магазині";
  public static final String BASE_MESSAGE = "Нові товари в нашому магазині:";
  private final SubscriptionService subscriptionService;
  private final SubscriberService subscriberService;
  private final SKUService skuService;
  private final EmailService emailService;

  @Override
  public void subscribe(String email) {
    Subscription subscription = subscriptionService.findById(ID).orElseThrow();
    subscriberService.save(Subscriber.builder()
        .subscription(subscription)
        .email(email)
        .build());
    log.info("Subscribe {} successfully", email);
  }

  @Override
  public void unsubscribe(String email) {
    Subscriber subscriber = subscriberService.findAllBySubscriptionIdAndEmail(ID, email)
        .orElseThrow(() -> new NotFoundException("email not found"));
    subscriberService.delete(subscriber);
    log.info("Unsubscribe {} successfully", email);
  }

  @Scheduled(cron = "${app.subscription.time.cron}", zone = "${app.subscription.time.zone}")
  @Override
  public void notifySubscribers() {

    List<String> subscribers = subscriberService.findAllBySubscriptionId(ID).stream()
        .map(Subscriber::getEmail).toList();
    final String message = skuToString();
    if (!BASE_MESSAGE.equals(message)) {
      subscribers.forEach(
          email -> emailService.sendEmail(email, BASE_TITLE, message)
      );
      log.info("Notify subscribers successfully");
    } else {
      log.info("There are no new SKU for subscribers notify");
    }
  }

  private String skuToString() {
    return skuService.findAllByStatus(true).stream()
        .reduce(
            BASE_MESSAGE,
            (s, sku) -> s + "\n   -"
                + sku.getProduct().getTitle() + "(" + skuCharacteristicsToString(sku) + ") - ціна "
                + sku.getProduct().getPrice().getValue()
                + sku.getProduct().getPrice().getCurrency().getCode(),
            String::concat);
  }

  private String skuCharacteristicsToString(SKU sku) {
    return sku.getCharacteristics().stream()
        .reduce(
            "",
            (s, skuCharacteristic) -> s + (s.equals("") ? "" : ", ")
                + skuCharacteristic.getCharacteristic().getTitle() + ": "
                + skuCharacteristic.getValue(),
            String::concat);
  }

}
