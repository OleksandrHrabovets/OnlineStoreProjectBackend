package ua.example.online_store.web.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ua.example.online_store.model.Product;
import ua.example.online_store.web.dto.ProductDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper extends Mappable<Product, ProductDto> {

}
