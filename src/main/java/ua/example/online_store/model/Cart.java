package ua.example.online_store.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Data
@Entity
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true, nullable = false)
  private String sessionId;
  @Transient
  @Digits(integer = 12, fraction = 0)
  private BigDecimal totalQuantity;
  @Transient
  @Digits(integer = 12, fraction = 0)
  private BigDecimal totalAmount;
  @OneToMany(fetch = FetchType.EAGER, targetEntity = CartItem.class, mappedBy = "cart")
  @Fetch(FetchMode.SUBSELECT)
  @OrderBy("id")
  private List<CartItem> items;

  public BigDecimal getTotalQuantity() {
    return items.stream()
        .map(CartItem::getQuantity)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public BigDecimal getTotalAmount() {
    return items.stream()
        .map(CartItem::getAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
