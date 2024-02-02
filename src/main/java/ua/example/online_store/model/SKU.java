package ua.example.online_store.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Data
@Entity
@EqualsAndHashCode(of = {"id"})
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
  @Column(name = "created_at", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
  @CreationTimestamp
  private LocalDateTime createdAt;
  @OneToMany(fetch = FetchType.EAGER, targetEntity = SKUCharacteristic.class, mappedBy = "sku")
  @Fetch(FetchMode.SUBSELECT)
  @OrderBy("characteristic.id")
  private List<SKUCharacteristic> characteristics;
  private boolean status;

}
