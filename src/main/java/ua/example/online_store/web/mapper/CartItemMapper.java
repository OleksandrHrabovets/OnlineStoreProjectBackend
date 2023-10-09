package ua.example.online_store.web.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ua.example.online_store.model.CartItem;
import ua.example.online_store.web.dto.CartItemDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CartItemMapper extends Mappable<CartItem, CartItemDto> {

}
