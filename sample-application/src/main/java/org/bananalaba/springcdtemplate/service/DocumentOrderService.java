package org.bananalaba.springcdtemplate.service;

import java.util.UUID;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.model.OrderDto;
import org.bananalaba.springcdtemplate.persistence.repository.mongodb.OrderDocumentsRepository;

@RequiredArgsConstructor
public class DocumentOrderService implements OrderService {

    @NonNull
    private final OrderDocumentsRepository repository;
    @NonNull
    private final OrderMapper mapper;

    @Override
    public String create(@NonNull OrderDto order) {
        var generatedId = UUID.randomUUID().toString();
        var model = mapper.toDocument(order.withId(generatedId));
        return repository.save(model).getId();
    }

    @Override
    public OrderDto get(@NonNull String id) {
        var model = repository.findById(id);
        return model.map(mapper::toDto)
            .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

}
