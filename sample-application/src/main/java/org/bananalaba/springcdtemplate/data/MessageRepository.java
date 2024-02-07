package org.bananalaba.springcdtemplate.data;

public interface MessageRepository extends OwnershipSubjectRepository {

    void save(String key, Message message);

    void delete(String key);

    @Override
    Message get(String key);

}
