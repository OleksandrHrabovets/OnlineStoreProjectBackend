package ua.example.online_store.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  @Column(name = "description", columnDefinition = "TEXT")
  private String description;
  @OneToOne(targetEntity = Price.class)
  private Price price;
  @ManyToOne(targetEntity = Category.class)
  private Category category;
  private boolean status;
  @Column(name = "created_at", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
  @CreationTimestamp
  private LocalDateTime createdAt;
  @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @Fetch(FetchMode.SUBSELECT)
  private List<SKU> skuSet;
  @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @Fetch(FetchMode.SUBSELECT)
  private List<Photo> photos;

}
