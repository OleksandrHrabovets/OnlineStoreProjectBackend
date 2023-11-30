package ua.example.online_store.web.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;
import ua.example.online_store.model.AvailableQuantity;
import ua.example.online_store.service.AvailableQuantityService;
import ua.example.online_store.service.SKUService;
import ua.example.online_store.web.dto.AvailableQuantityDto;
import ua.example.online_store.web.mapper.AvailableQuantityMapper;

@Slf4j
@RestController
@RequestMapping("/api/v1/available_quantities")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class AvailableQuantityController {

  public static final String INVOKED_METHOD = "invoked method {}";
  private final AvailableQuantityService availableQuantityService;
  private final AvailableQuantityMapper availableQuantityMapper;
  private final SKUService skuService;

  @GetMapping
  public ResponseEntity<List<AvailableQuantityDto>> getAll() {
    log.info(INVOKED_METHOD, "getAll()");
    return ResponseEntity.ok(availableQuantityMapper.toDto(availableQuantityService.getAll()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<AvailableQuantityDto> getBySkuId(@PathVariable Long id) {
    log.info(INVOKED_METHOD, "getBySkuId()");
    return ResponseEntity.ok(availableQuantityMapper.toDto(availableQuantityService.findBySkuId(id)
        .orElse(AvailableQuantity.builder()
            .sku(skuService.findById(id)
                .orElseThrow(() -> new NotFoundException("SKU not found")))
            .quantity(BigDecimal.ZERO)
            .build())));
  }

  @PostMapping
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public ResponseEntity<AvailableQuantityDto> setAvailableQuantity(
      @Valid @RequestBody AvailableQuantityDto availableQuantityDto) {
    log.info(INVOKED_METHOD, "manageAvailableQuantity()");
    return ResponseEntity.ok(availableQuantityMapper.toDto(
        availableQuantityService.setAvailableQuantity(availableQuantityDto)));
  }
}
