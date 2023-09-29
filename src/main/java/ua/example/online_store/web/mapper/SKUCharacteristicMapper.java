package ua.example.online_store.web.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ua.example.online_store.model.SKUCharacteristic;
import ua.example.online_store.web.dto.SKUCharacteristicDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SKUCharacteristicMapper extends Mappable<SKUCharacteristic, SKUCharacteristicDto> {

}
