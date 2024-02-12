package org.bananalaba.springcdtemplate.data;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Component;

@Component
public class InMemoryMessageRepository implements MessageRepository {

    private final ConcurrentMap<String, Message> memory = new ConcurrentHashMap<>();

    @Override
    public void save(String key, Message message) {
        notBlank(key, "message key required");
        notNull(message, "message cannot be null");

        var existing = memory.get(key);
        if (existing != null) {
            isTrue(
                existing.getOwnerId().equals(message.getOwnerId()),
                "cannot change owner of message " + key + " from " + existing.getOwnerId() + " to " + message.getOwnerId()
            );
        }

        memory.put(key, message);
    }

    @Override
    public void delete(final String key) {
        notBlank(key, "message key required");

        var removed = memory.remove(key);
        if (removed == null) {
            throw new IllegalArgumentException("message with key=" + key + " not found");
        }
    }

    @Override
    public Message get(String key) {
        notBlank(key, "message key required");

        var message = memory.get(key);
        if (message == null) {
            throw new IllegalArgumentException("message with key=" + key + " not found");
        }

        return message;
    }

}
