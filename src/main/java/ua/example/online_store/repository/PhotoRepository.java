package ua.example.online_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.example.online_store.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

}
