package org.bananalaba.springcdtemplate.security;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Component;

@Component
public class InMemoryAclRepository implements AclRepository {

    private final ConcurrentMap<String, AclRecord> records = new ConcurrentHashMap<>();

    @Override
    public void save(final AclRecord record) {
        notNull(record, "record cannot be null");
        records.put(recordId(record), record);
    }

    private String recordId(AclRecord record) {
        return recordId(record.getSubjectType(), record.getSubjectKey());
    }

    @Override
    public AclRecord get(final String subjectType, final String subjectKey) {
        notBlank(subjectType, "subjectType required");
        notBlank(subjectKey, "subjectKey required");

        return records.get(recordId(subjectType, subjectKey));
    }

    private String recordId(final String subjectType, final String subjectKey) {
        return subjectType + "_" + subjectKey;
    }

}
