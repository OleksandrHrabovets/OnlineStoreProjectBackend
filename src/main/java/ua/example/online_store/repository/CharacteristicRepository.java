package ua.example.online_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.example.online_store.model.Characteristic;

public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {

}
