package org.bananalaba.springcdtemplate.persistence.repository.jdbc;

import org.bananalaba.springcdtemplate.persistence.model.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderEntitiesRepository extends CrudRepository<OrderEntity, Long> {
}
