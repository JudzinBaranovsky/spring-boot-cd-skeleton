package org.bananalaba.springcdtemplate.security;

public interface AccessControlRepository {

    void save(final AccessControlRecord record);

    AccessControlRecord getForMessage(final String messageKey);

}
