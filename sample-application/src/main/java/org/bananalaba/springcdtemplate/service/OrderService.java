package org.bananalaba.springcdtemplate.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bananalaba.springcdtemplate.model.OrderDto;
import org.bananalaba.springcdtemplate.model.OrderLineItemDto;
import org.bananalaba.springcdtemplate.persistence.model.Order;
import org.bananalaba.springcdtemplate.persistence.model.OrderLineItem;
import org.bananalaba.springcdtemplate.persistence.repository.OrderRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderService {

    @NonNull
    private final OrderRepository repository;

    public long create(@NonNull final OrderDto order) {
        var model = toModel(order);
        return repository.save(model).getId();
    }

    public OrderDto get(final long id) {
        var model = repository.findById(id);
        return model.map(this::toDto)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    private Order toModel(final OrderDto dto) {
        return new Order(
            dto.getId(),
            System.currentTimeMillis(),
            toModels(dto.getLineItems())
        );
    }

    private Set<OrderLineItem> toModels(final List<OrderLineItemDto> dtos) {
        return dtos.stream()
            .map(this::toModel)
            .collect(Collectors.toSet());
    }

    private OrderLineItem toModel(final OrderLineItemDto dto) {
        return new OrderLineItem(null, dto.getProductName(), dto.getQuantity(), dto.getPriceUsd());
    }

    private OrderDto toDto(final Order order) {
        return new OrderDto(order.getId(), toDtos(order.getLineItems()));
    }

    private List<OrderLineItemDto> toDtos(final Set<OrderLineItem> models) {
        return models.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    private OrderLineItemDto toDto(final OrderLineItem model) {
        return new OrderLineItemDto(model.getProductName(), model.getQuantity(), model.getPriceUsd());
    }

}
