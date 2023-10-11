package ua.example.online_store.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.example.online_store.model.Photo;
import ua.example.online_store.repository.PhotoRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoService {

  public static final String INVOKED_METHOD = "invoked method {}";
  private final PhotoRepository photoRepository;

  public Optional<Photo> findById(Long id) {
    return photoRepository.findById(id);
  }

  public List<Photo> saveAll(List<Photo> photos) {
    log.info(INVOKED_METHOD, "saveAll()");
    return photoRepository.saveAll(photos);
  }
}
