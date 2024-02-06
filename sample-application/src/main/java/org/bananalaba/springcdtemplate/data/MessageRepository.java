package org.bananalaba.springcdtemplate.data;

public interface MessageRepository {

    void save(String key, Message message);

    void delete(String key);

    Message get(String key);

}
