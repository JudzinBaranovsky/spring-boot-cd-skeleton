package org.bananalaba.springcdtemplate.data.repository;

import org.bananalaba.springcdtemplate.data.model.MessageRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "messages", path = "messages")
public interface MessageRecordRepository extends CrudRepository<MessageRecord, String> {
}
