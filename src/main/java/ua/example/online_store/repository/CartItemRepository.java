package ua.example.online_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.example.online_store.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
