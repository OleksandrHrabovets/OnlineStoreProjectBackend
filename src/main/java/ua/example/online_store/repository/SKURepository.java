package ua.example.online_store.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.example.online_store.model.SKU;

public interface SKURepository extends JpaRepository<SKU, Long> {

  List<SKU> findAllByStatus(boolean status);

}
