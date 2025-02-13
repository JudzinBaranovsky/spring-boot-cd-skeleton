package org.bananalaba.springcdtemplate.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bananalaba.springcdtemplate.model.OrderDto;
import org.bananalaba.springcdtemplate.persistence.repository.OrderRepository;

@AllArgsConstructor
public class SqlOrderService implements OrderService {

    @NonNull
    private final OrderRepository repository;
    @NonNull
    private final OrderMapper mapper;

    public long create(@NonNull final OrderDto order) {
        var model = mapper.toEntity(order);
        return repository.save(model).getId();
    }

    public OrderDto get(final long id) {
        var model = repository.findById(id);
        return model.map(mapper::toDto)
            .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

}
