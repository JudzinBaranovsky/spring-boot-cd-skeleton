package org.bananalaba.springcdtemplate.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.bananalaba.springcdtemplate.data.Message;
import org.springframework.stereotype.Component;

@Component
public class InMemoryMessageService implements MessageService {

    private final ConcurrentMap<String, Message> storage = new ConcurrentHashMap<>();

    @Override
    public void save(String key, Message message) {
        storage.put(key, message);
    }

    @Override
    public void delete(String key) {
        storage.remove(key);
    }

    @Override
    public Message get(String key) {
        return storage.get(key);
    }

}
