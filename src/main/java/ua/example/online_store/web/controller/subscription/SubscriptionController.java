package ua.example.online_store.web.controller.subscription;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;
import ua.example.online_store.model.subscription.Subscriber;
import ua.example.online_store.model.subscription.Subscription;
import ua.example.online_store.service.subscription.SubscriberService;
import ua.example.online_store.service.subscription.SubscriptionService;
import ua.example.online_store.web.dto.subscription.SubscriberDto;

@Slf4j
@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

  public static final String INVOKED_METHOD = "invoked method {}";
  private final SubscriptionService subscriptionService;
  private final SubscriberService subscriberService;

  @GetMapping
  public ResponseEntity<List<Subscription>> getAll() {
    log.info(INVOKED_METHOD, "getAll()");
    return ResponseEntity.ok(subscriptionService.getAll());
  }

  @PostMapping
  public ResponseEntity<Subscriber> subscribe(@Valid @RequestBody SubscriberDto subscriberDto) {
    log.info(INVOKED_METHOD, "subscribe()");
    Subscriber subscriber = subscriberService.findAllBySubscriptionIdAndEmail(
            subscriberDto.getSubscriptionId(), subscriberDto.getEmail())
        .orElse(Subscriber.builder()
            .subscription(subscriptionService.findById(subscriberDto.getSubscriptionId())
                .orElseThrow(() -> new NotFoundException("subscription not found")))
            .email(subscriberDto.getEmail())
            .build());
    return ResponseEntity.ok(subscriberService.save(subscriber));
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  public void unsubscribe(@Valid @RequestBody SubscriberDto subscriberDto) {
    log.info(INVOKED_METHOD, "unsubscribe()");
    Subscriber subscriber = subscriberService.findAllBySubscriptionIdAndEmail(
            subscriberDto.getSubscriptionId(), subscriberDto.getEmail())
        .orElseThrow(() -> new NotFoundException("subscriber not found"));
    subscriberService.delete(subscriber);
  }


}
