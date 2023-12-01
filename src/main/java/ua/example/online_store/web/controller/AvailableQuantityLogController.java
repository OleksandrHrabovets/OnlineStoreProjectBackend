package ua.example.online_store.web.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.example.online_store.model.AvailableQuantityLog;
import ua.example.online_store.service.AvailableQuantityLogService;

@Slf4j
@RestController
@RequestMapping("/api/v1/available_quantities/log")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class AvailableQuantityLogController {

  public static final String INVOKED_METHOD = "invoked method {}";
  private final AvailableQuantityLogService availableQuantityLogService;

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @GetMapping
  public ResponseEntity<List<AvailableQuantityLog>> getAll() {
    log.info(INVOKED_METHOD, "getAll()");
    return ResponseEntity.ok(availableQuantityLogService.getAll());
  }

}
