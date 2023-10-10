package ua.example.online_store.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.example.online_store.model.AvailableQuantity;

public interface AvailableQuantityRepository extends JpaRepository<AvailableQuantity, Long> {

  Optional<AvailableQuantity> findBySkuId(Long skuId);

}
