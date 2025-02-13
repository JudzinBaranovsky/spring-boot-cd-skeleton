package org.bananalaba.springcdtemplate.persistence.repository;

import org.bananalaba.springcdtemplate.persistence.model.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
}
