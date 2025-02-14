package org.bananalaba.springcdtemplate.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bananalaba.springcdtemplate.model.OrderDto;
import org.bananalaba.springcdtemplate.persistence.repository.jdbc.OrderEntitiesRepository;

@AllArgsConstructor
public class SqlOrderService implements OrderService {

    @NonNull
    private final OrderEntitiesRepository repository;
    @NonNull
    private final OrderMapper mapper;

    @Override
    public String create(@NonNull final OrderDto order) {
        var model = mapper.toEntity(order);
        var generatedId = repository.save(model).getId();

        return String.valueOf(generatedId);
    }

    @Override
    public OrderDto get(@NonNull final String id) {
        var numericId = Long.valueOf(id);
        var model = repository.findById(numericId);
        return model.map(mapper::toDto)
            .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

}
