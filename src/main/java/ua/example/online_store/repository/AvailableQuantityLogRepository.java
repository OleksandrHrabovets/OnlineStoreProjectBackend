package ua.example.online_store.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.example.online_store.model.AvailableQuantityLog;

public interface AvailableQuantityLogRepository extends JpaRepository<AvailableQuantityLog, Long> {

  List<AvailableQuantityLog> findAllBySkuId(Long skuId);

}
