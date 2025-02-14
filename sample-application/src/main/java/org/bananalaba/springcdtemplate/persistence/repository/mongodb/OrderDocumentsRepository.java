package org.bananalaba.springcdtemplate.persistence.repository.mongodb;

import org.bananalaba.springcdtemplate.persistence.model.document.OrderDocument;
import org.springframework.data.repository.CrudRepository;

public interface OrderDocumentsRepository extends CrudRepository<OrderDocument, String> {
}
