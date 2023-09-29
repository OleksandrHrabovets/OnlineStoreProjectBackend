package ua.example.online_store.web.mapper;

import java.util.List;

public interface Mappable<E, D> {

  E toEntity(D dto);

  List<E> toEntity(List<D> d);

  D toDto(E entity);

  List<D> toDto(List<E> e);

}
