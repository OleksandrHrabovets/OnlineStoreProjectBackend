package ua.example.online_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.example.online_store.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
