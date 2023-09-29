package ua.example.online_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.example.online_store.model.Price;

public interface PriceRepository extends JpaRepository<Price, Long> {

}
