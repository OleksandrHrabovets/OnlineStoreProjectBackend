package ua.example.online_store.web.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ua.example.online_store.model.Currency;
import ua.example.online_store.web.dto.CurrencyDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CurrencyMapper extends Mappable<Currency, CurrencyDto> {

}
