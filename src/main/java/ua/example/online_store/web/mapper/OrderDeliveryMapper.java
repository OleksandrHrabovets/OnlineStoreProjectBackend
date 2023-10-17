package ua.example.online_store.web.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ua.example.online_store.model.OrderDelivery;
import ua.example.online_store.web.dto.OrderDeliveryDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface OrderDeliveryMapper extends Mappable<OrderDelivery, OrderDeliveryDto> {

}
