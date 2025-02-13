package org.bananalaba.springcdtemplate.service;

import lombok.NonNull;
import org.bananalaba.springcdtemplate.model.OrderDto;

public interface OrderService {

    long create(@NonNull final OrderDto order);

    OrderDto get(final long id);

}
