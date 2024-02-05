package org.bananalaba.springcdtemplate.security;

import static org.apache.commons.lang3.Validate.notNull;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Component;

@Component
public class InMemoryAccessControlRepository implements AccessControlRepository {

    private final ConcurrentMap<String, AccessControlRecord> records = new ConcurrentHashMap<>();

    @Override
    public void save(AccessControlRecord record) {
        notNull(record, "record cannot be null");
        records.put(record.getMessageKey(), record);
    }

    @Override
    public AccessControlRecord getForMessage(String messageKey) {
        var record = records.get(messageKey);
        notNull(record, "no record for message key=" + messageKey);

        return record;
    }

}
