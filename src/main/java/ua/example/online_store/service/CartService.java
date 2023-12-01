package ua.example.online_store.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ua.example.online_store.model.Cart;
import ua.example.online_store.model.CartItem;
import ua.example.online_store.model.SKU;
import ua.example.online_store.repository.CartItemRepository;
import ua.example.online_store.repository.CartRepository;
import ua.example.online_store.web.dto.CartAddItemDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

  public static final String INVOKED_METHOD = "invoked method {}";
  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  private final SKUService skuService;
  private final AvailableQuantityService availableQuantityService;

  public List<Cart> getAll() {
    log.info(INVOKED_METHOD, "getAll()");
    return cartRepository.findAll();
  }

  public Optional<Cart> getBySessionId(String sessionId) {
    log.info(INVOKED_METHOD, "getBySessionId()");
    return cartRepository.findBySessionId(sessionId);
  }

  @Transactional
  public Cart add(CartAddItemDto cartAddItemDto) {
    log.info(INVOKED_METHOD, "add()");
    Cart cart = cartRepository.findBySessionId(cartAddItemDto.getSessionId())
        .orElse(Cart.builder()
            .sessionId(cartAddItemDto.getSessionId())
            .items(new ArrayList<>())
            .build());
    SKU sku = skuService.findById(cartAddItemDto.getSkuId())
        .orElseThrow(() -> new NotFoundException("SKU not found"));

    validate(cartAddItemDto, sku);

    CartItem cartItem = cart.getItems().stream()
        .filter(item -> item.getSku().equals(sku)).findFirst().orElse(
            CartItem.builder()
                .cart(cart)
                .sku(sku)
                .price(BigDecimal.ZERO)
                .quantity(BigDecimal.ZERO)
                .amount(BigDecimal.ZERO)
                .build());
    cartItem.setPrice(cartAddItemDto.getPrice());
    cartItem.setQuantity(cartItem.getQuantity().add(cartAddItemDto.getQuantity()));
    cartItem.setAmount(cartItem.getAmount().add(cartAddItemDto.getAmount()));
    cartItemRepository.save(cartItem);
    return cartRepository.save(cart);
  }

  private void validate(CartAddItemDto cartAddItemDto, SKU sku) {
    availableQuantityService.checkAvailableQuantity(sku, cartAddItemDto.getQuantity());
    BigDecimal productPrice = sku.getProduct().getPrice().getValue();
    if (productPrice.compareTo(cartAddItemDto.getPrice()) != 0) {
      throw new IllegalArgumentException("Price %s does not correspond to the product price %s"
          .formatted(cartAddItemDto.getPrice(), productPrice));
    }
    if (cartAddItemDto.getQuantity().multiply(cartAddItemDto.getPrice())
        .compareTo(cartAddItemDto.getAmount()) != 0) {
      throw new IllegalArgumentException("Amount does not correspond to the quantity and price");
    }
  }

  public void delete(String sessionId, Long skuId) {
    log.info(INVOKED_METHOD, "delete()");
    Cart cart = cartRepository.findBySessionId(sessionId)
        .orElseThrow(() -> new NotFoundException("sessionId not found"));
    cartItemRepository.delete(cart.getItems().stream().
        filter(cartItem ->
            cartItem.getSku().getId().equals(skuId)).findFirst()
        .orElseThrow(() -> new NotFoundException("sku not found")));
  }

  public void clear(String sessionId) {
    log.info(INVOKED_METHOD, "clear()");
    Cart cart = cartRepository.findBySessionId(sessionId)
        .orElseThrow(() -> new NotFoundException("sessionId not found"));
    cartItemRepository.deleteAll(cart.getItems());
  }

}
