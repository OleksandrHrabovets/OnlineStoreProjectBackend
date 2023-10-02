package ua.example.online_store.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SKUCharacteristic {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(targetEntity = SKU.class)
  private SKU sku;
  @ManyToOne(targetEntity = Characteristic.class)
  private Characteristic characteristic;
  private String value;

  public void setSku(SKU sku) {
    this.sku = sku;
    this.sku.getCharacteristics().add(this);
  }

}
