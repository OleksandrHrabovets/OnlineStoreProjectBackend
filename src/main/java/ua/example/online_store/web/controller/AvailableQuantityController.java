package ua.example.online_store.web.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.example.online_store.service.AvailableQuantityService;
import ua.example.online_store.web.dto.AvailableQuantityDto;
import ua.example.online_store.web.mapper.AvailableQuantityMapper;

@Slf4j
@RestController
@RequestMapping("/api/v1/available_quantities")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class AvailableQuantityController {

  private final AvailableQuantityService availableQuantityService;
  private final AvailableQuantityMapper availableQuantityMapper;

  @GetMapping
  public ResponseEntity<List<AvailableQuantityDto>> getAll() {
    log.info("invoked method {}", "getAll()");
    return ResponseEntity.ok(availableQuantityMapper.toDto(availableQuantityService.getAll()));
  }

  @PostMapping
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public ResponseEntity<AvailableQuantityDto> setAvailableQuantity(
      @Valid @RequestBody AvailableQuantityDto availableQuantityDto) {
    log.info("invoked method {}", "manageAvailableQuantity()");
    return ResponseEntity.ok(availableQuantityMapper.toDto(
        availableQuantityService.setAvailableQuantity(availableQuantityDto)));
  }
}
