package ua.example.online_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.example.online_store.model.SKUCharacteristic;

public interface SKUCharacteristicRepository extends JpaRepository<SKUCharacteristic, Long> {

}
