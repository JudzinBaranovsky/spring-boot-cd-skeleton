package org.bananalaba.springcdtemplate.service;

import lombok.NonNull;
import org.bananalaba.springcdtemplate.model.OrderDto;
import org.bananalaba.springcdtemplate.persistence.model.document.OrderDocument;
import org.bananalaba.springcdtemplate.persistence.model.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface OrderMapper {

    OrderDto toDto(@NonNull final OrderEntity order);

    OrderDto toDto(@NonNull final OrderDocument order);

    @Mapping(target = "id", ignore = true)
    OrderEntity toEntity(@NonNull final OrderDto dto);

    @Mapping(target = "id", ignore = true)
    OrderDocument toDocument(@NonNull final OrderDto dto);

}
