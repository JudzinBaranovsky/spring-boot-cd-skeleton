package org.bananalaba.springcdtemplate.service;

import lombok.NonNull;
import org.bananalaba.springcdtemplate.model.OrderDto;

public interface OrderService {

    String create(@NonNull final OrderDto order);

    OrderDto get(final String id);

}
