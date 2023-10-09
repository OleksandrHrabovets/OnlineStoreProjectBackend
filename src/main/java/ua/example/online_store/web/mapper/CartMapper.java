package ua.example.online_store.web.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ua.example.online_store.model.Cart;
import ua.example.online_store.web.dto.CartDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CartMapper extends Mappable<Cart, CartDto> {

}
