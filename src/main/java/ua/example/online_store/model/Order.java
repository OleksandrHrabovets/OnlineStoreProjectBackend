package ua.example.online_store.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
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
import ua.example.online_store.model.enums.OrderStatus;

@Data
@Entity
@Table(name = "orders")
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String sessionId;
  @Transient
  @Digits(integer = 12, fraction = 0)
  private BigDecimal totalQuantity;
  @Transient
  @Digits(integer = 12, fraction = 0)
  private BigDecimal totalAmount;
  @Enumerated(EnumType.STRING)
  private OrderStatus status;
  @OneToOne(targetEntity = OrderDelivery.class)
  private OrderDelivery delivery;
  @OneToMany(fetch = FetchType.EAGER, targetEntity = OrderItem.class, mappedBy = "order")
  @Fetch(FetchMode.SUBSELECT)
  @OrderBy("id")
  private List<OrderItem> items;

  public BigDecimal getTotalQuantity() {
    return items.stream()
        .map(OrderItem::getQuantity)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public BigDecimal getTotalAmount() {
    return items.stream()
        .map(OrderItem::getAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
