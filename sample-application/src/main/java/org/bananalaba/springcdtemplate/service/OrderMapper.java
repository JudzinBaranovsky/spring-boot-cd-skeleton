package org.bananalaba.springcdtemplate.service;

import lombok.NonNull;
import org.bananalaba.springcdtemplate.model.OrderDto;
import org.bananalaba.springcdtemplate.persistence.model.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface OrderMapper {

    OrderDto toDto(@NonNull final OrderEntity order);

    OrderEntity toEntity(@NonNull final OrderDto dto);

}
