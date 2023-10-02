package ua.example.online_store.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
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
public class SKU {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "product_id", referencedColumnName = "id")
  private Product product;
  @OneToMany(targetEntity = SKUCharacteristic.class, mappedBy = "sku")
  private List<SKUCharacteristic> characteristics;
  private boolean status;

}
