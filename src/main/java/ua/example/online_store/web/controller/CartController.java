package ua.example.online_store.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;
import ua.example.online_store.service.CartService;
import ua.example.online_store.web.dto.CartAddItemDto;
import ua.example.online_store.web.dto.CartDto;
import ua.example.online_store.web.mapper.CartMapper;

@Slf4j
@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

  public static final String INVOKED_METHOD = "invoked method {}";
  private final CartService cartService;
  private final CartMapper cartMapper;

  @GetMapping
  public ResponseEntity<CartDto> get(@RequestParam String sessionId) {
    log.info(INVOKED_METHOD, "get()");
    return ResponseEntity.ok(cartMapper.toDto(cartService.getBySessionId(sessionId)
        .orElseThrow(() -> new NotFoundException("sessionId not found"))));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public void add(@RequestBody CartAddItemDto cartAddItemDto) {

    log.info(INVOKED_METHOD, "add()");
    cartService.add(cartAddItemDto);

  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  public void delete(
      @RequestParam String sessionId,
      @RequestParam Long skuId) {
    log.info(INVOKED_METHOD, "delete()");
    cartService.delete(sessionId, skuId);
  }

  @DeleteMapping("/clear")
  @ResponseStatus(HttpStatus.OK)
  public void clear(@RequestParam String sessionId) {
    log.info(INVOKED_METHOD, "clear()");
    cartService.clear(sessionId);
  }

}

