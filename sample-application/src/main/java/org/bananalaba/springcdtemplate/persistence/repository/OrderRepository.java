package org.bananalaba.springcdtemplate.persistence.repository;

import org.bananalaba.springcdtemplate.persistence.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
