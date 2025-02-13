package org.bananalaba.springcdtemplate.service;

import lombok.NonNull;
import org.bananalaba.springcdtemplate.model.OrderDto;
import org.bananalaba.springcdtemplate.persistence.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface OrderMapper {

    OrderDto toDto(@NonNull final Order order);

    Order toEntity(@NonNull final OrderDto dto);

}
