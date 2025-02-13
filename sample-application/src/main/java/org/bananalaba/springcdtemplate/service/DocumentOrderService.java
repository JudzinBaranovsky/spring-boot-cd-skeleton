package org.bananalaba.springcdtemplate.service;

import lombok.NonNull;
import org.bananalaba.springcdtemplate.model.OrderDto;

public class DocumentOrderService implements OrderService {

    @Override
    public long create(@NonNull OrderDto order) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OrderDto get(long id) {
        throw new UnsupportedOperationException();
    }

}
