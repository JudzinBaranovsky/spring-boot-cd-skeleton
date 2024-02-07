package org.bananalaba.springcdtemplate.security;

public interface AclRepository {

    void save(final AclRecord record);

    AclRecord get(final String subjectType, final String subjectKey);

}
