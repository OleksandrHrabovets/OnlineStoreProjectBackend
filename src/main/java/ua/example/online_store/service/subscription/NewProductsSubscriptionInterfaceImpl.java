package ua.example.online_store.service.subscription;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.webjars.NotFoundException;
import ua.example.online_store.model.Photo;
import ua.example.online_store.model.Product;
import ua.example.online_store.model.SKU;
import ua.example.online_store.model.SKUCharacteristic;
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
  private final SubscriptionService subscriptionService;
  private final SubscriberService subscriberService;
  private final SKUService skuService;
  private final EmailService emailService;
  private final TemplateEngine templateEngine;

  @Value("${app.front.url}")
  private String frontUrl;

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
    List<NewProductGroup> newProducts = getNewProducts();
    final String message = templateEngine.process("email-subscription-new-products",
        getContext(newProducts));
    if (!newProducts.isEmpty()) {
      subscribers.forEach(
          email -> emailService.sendEmail(email, BASE_TITLE, message
              .replace("[unsubscribeLink]", unsubscribeLink(email))
          ));
      log.info("Notify subscribers successfully");
    } else {
      log.info("There are no new SKU for subscribers notify");
    }
  }

  private Context getContext(List<NewProductGroup> newProducts) {

    Context context = new Context();
    context.setVariable("newProducts", newProducts);
    return context;
  }

  private List<NewProductGroup> getNewProducts() {
    return skuService.findAllCreatedIsAfter(LocalDateTime.now().minusDays(1)).stream()
        .map(NewProductsSubscriptionInterfaceImpl::mapToNewProduct)
        .collect(Collectors.groupingBy(newProduct -> newProduct.product))
        .entrySet().stream()
        .map(NewProductsSubscriptionInterfaceImpl::mapToNewProductGroup
        )
        .toList();
  }

  private static NewProduct mapToNewProduct(SKU sku) {
    return new NewProduct(sku.getProduct(),
        sku.getProduct().getTitle(),
        sku.getCharacteristics().stream()
            .filter(skuCharacteristic -> skuCharacteristic.getCharacteristic().getTitle()
                .equals("color")).findFirst().orElse(
                SKUCharacteristic.builder().value("").build()).getValue(),
        sku.getCharacteristics().stream()
            .filter(skuCharacteristic -> skuCharacteristic.getCharacteristic().getTitle()
                .equals("size")).findFirst().orElse(
                SKUCharacteristic.builder().value("").build()).getValue(),
        sku.getProduct().getPhotos().stream()
            .findFirst().orElse(Photo.builder().url("").build()).getUrl(),
        sku.getProduct().getPrice().getValue(),
        sku.getProduct().getPrice().getCurrency().getCode());
  }

  private static NewProductGroup mapToNewProductGroup(Entry<Product, List<NewProduct>> entry) {
    return new NewProductGroup(entry.getKey().getTitle(),
        entry.getValue().stream()
            .map(newProduct -> newProduct.color)
            .collect(Collectors.toSet()),
        entry.getValue().stream()
            .map(newProduct -> newProduct.size)
            .collect(Collectors.toSet()),
        entry.getValue().stream().limit(1).map(newProduct -> newProduct.photoUrl)
            .findFirst().orElse(""),
        entry.getValue().stream().limit(1).map(newProduct -> newProduct.price)
            .findFirst().orElse(BigDecimal.ZERO),
        entry.getValue().stream().limit(1).map(newProduct -> newProduct.currencyCode)
            .findFirst().orElse(""));
  }

  record NewProduct(Product product, String title, String color, String size, String photoUrl,
                    BigDecimal price, String currencyCode) {

  }

  record NewProductGroup(String title, Set<String> color, Set<String> size, String photoUrl,
                         BigDecimal price, String currencyCode) {

  }

  private String unsubscribeLink(String email) {
    return frontUrl + "/unsubscribe?email=" + email;
  }
}
