package ua.example.online_store.web.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.example.online_store.model.enums.OrderStatus;
import ua.example.online_store.service.OrderService;
import ua.example.online_store.web.dto.OrderDeliveryDto;
import ua.example.online_store.web.dto.OrderDto;
import ua.example.online_store.web.mapper.OrderMapper;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class OrderController {

  public static final String INVOKED_METHOD = "invoked method {}";
  private final OrderService orderService;
  private final OrderMapper orderMapper;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public void makeOrder(@RequestParam String sessionId,
      @Valid @RequestBody OrderDeliveryDto orderDeliveryDto) {

    log.info(INVOKED_METHOD, "makeOrder()");
    orderService.makeOrder(sessionId, orderDeliveryDto);

  }

  @PutMapping
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public ResponseEntity<OrderDto> changeOrderStatus(@RequestParam Long orderId,
      @RequestParam OrderStatus status) {

    log.info(INVOKED_METHOD, "changeOrderStatus()");
    return ResponseEntity.ok(orderMapper.toDto(orderService.changeOrderStatus(orderId, status)));

  }

}

