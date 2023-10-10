package ua.example.online_store.web.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ua.example.online_store.model.AvailableQuantity;
import ua.example.online_store.web.dto.AvailableQuantityDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AvailableQuantityMapper extends Mappable<AvailableQuantity, AvailableQuantityDto> {

}
