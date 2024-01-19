package ua.example.online_store.web.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RestController;
import ua.example.online_store.model.AvailableQuantity;
import ua.example.online_store.model.enums.OrderStatus;
import ua.example.online_store.service.AvailableQuantityService;
import ua.example.online_store.service.OrderService;
import ua.example.online_store.web.dto.OrderDeliveryDto;
import ua.example.online_store.web.dto.OrderDto;
import ua.example.online_store.web.dto.OrderItemDto;
import ua.example.online_store.web.mapper.OrderMapper;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class OrderController {

  public static final String INVOKED_METHOD = "invoked method {}";
  private final OrderService orderService;
  private final AvailableQuantityService availableQuantityService;
  private final OrderMapper orderMapper;

  @PostMapping
  public ResponseEntity<OrderDto> makeOrder(@RequestParam String sessionId,
      @Valid @RequestBody OrderDeliveryDto orderDeliveryDto) {

    log.info(INVOKED_METHOD, "makeOrder()");
    OrderDto orderDto = orderMapper.toDto(orderService.makeOrder(sessionId, orderDeliveryDto));
    orderDto.getItems().forEach(this::setAvailableQuantity);
    return ResponseEntity.status(HttpStatus.CREATED).body(
        orderDto);

  }

  @PutMapping
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public ResponseEntity<OrderDto> changeOrderStatus(@RequestParam Long orderId,
      @RequestParam OrderStatus status) {

    log.info(INVOKED_METHOD, "changeOrderStatus()");
    OrderDto orderDto = orderMapper.toDto(orderService.changeOrderStatus(orderId, status));
    orderDto.getItems().forEach(this::setAvailableQuantity);
    return ResponseEntity.ok(orderDto);

  }

  private void setAvailableQuantity(OrderItemDto orderItemDto) {
    orderItemDto.getSku().setAvailableQuantity(
        availableQuantityService.findBySkuId(orderItemDto.getSku().getId())
            .orElse(AvailableQuantity.builder()
                .quantity(BigDecimal.ZERO)
                .build()).getQuantity());
  }
}

