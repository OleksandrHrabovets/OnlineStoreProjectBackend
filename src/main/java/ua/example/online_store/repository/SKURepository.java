package ua.example.online_store.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.example.online_store.model.SKU;

public interface SKURepository extends JpaRepository<SKU, Long> {

  @Query("FROM SKU s JOIN FETCH s.characteristics JOIN FETCH s.product "
      + "WHERE s.status = :status")
  List<SKU> findAllByStatus(@Param("status") boolean status);

}
