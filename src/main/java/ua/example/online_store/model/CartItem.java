package ua.example.online_store.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Digits;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(targetEntity = Cart.class)
  private Cart cart;
  @OneToOne
  private SKU sku;
  @Digits(integer = 12, fraction = 0)
  private BigDecimal price;
  @Digits(integer = 12, fraction = 0)
  private BigDecimal quantity;
  @Digits(integer = 12, fraction = 0)
  private BigDecimal amount;
  @Transient
  private Long productId;
  @Transient
  private String productTitle;
  @Transient
  private String photoUrl;

  public Long getProductId() {
    return sku.getProduct().getId();
  }

  public String getProductTitle() {
    return sku.getProduct().getTitle();
  }

  public String getPhotoUrl() {
    String colorName = sku.getCharacteristics().stream()
        .filter(skuCharacteristic -> skuCharacteristic.getCharacteristic().getTitle()
            .equals("color")).findFirst().orElse(
            SKUCharacteristic.builder().value("").build()).getValue();

    return sku.getProduct().getPhotos().stream()
        .filter(photo -> photo.getColor() != null && photo.getColor().name().equals(colorName))
        .findFirst()
        .orElse(Photo.builder().url("").build()).getUrl();
  }
}
