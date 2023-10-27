package ua.example.online_store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.example.online_store.model.OrderDelivery;
import ua.example.online_store.repository.OrderDeliveryRepository;
import ua.example.online_store.web.dto.OrderDeliveryDto;
import ua.example.online_store.web.mapper.OrderDeliveryMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderDeliveryService {

  public static final String INVOKED_METHOD = "invoked method {}";
  private final OrderDeliveryRepository orderDeliveryRepository;
  private final OrderDeliveryMapper orderDeliveryMapper;

  public OrderDelivery save(OrderDeliveryDto orderDeliveryDto) {
    log.info(INVOKED_METHOD, "save()");
    OrderDelivery delivery = orderDeliveryMapper.toEntity(orderDeliveryDto);
    delivery.setId(null);
    return orderDeliveryRepository.save(delivery);
  }

}
