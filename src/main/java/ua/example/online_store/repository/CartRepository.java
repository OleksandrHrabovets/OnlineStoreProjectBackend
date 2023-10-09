package ua.example.online_store.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.example.online_store.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

  Optional<Cart> findBySessionId(String sessionId);

}
