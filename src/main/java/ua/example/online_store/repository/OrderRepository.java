package ua.example.online_store.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.example.online_store.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findAllBySessionId(String sessionId);

}
