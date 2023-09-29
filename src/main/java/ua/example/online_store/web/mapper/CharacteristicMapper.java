package ua.example.online_store.web.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ua.example.online_store.model.Characteristic;
import ua.example.online_store.web.dto.CharacteristicDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CharacteristicMapper extends Mappable<Characteristic, CharacteristicDto> {

}
