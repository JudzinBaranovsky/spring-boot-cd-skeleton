package org.bananalaba.springcdtemplate.service;

import org.bananalaba.springcdtemplate.data.Message;
import org.bananalaba.springcdtemplate.data.MessageUpdate;
import org.bananalaba.springcdtemplate.security.OwnershipSubjectType;
import org.springframework.security.access.prepost.PreAuthorize;

@OwnershipSubjectType("message")
public interface MessageService {

    @PreAuthorize("canEditOrCreate(#key)")
    void save(String key, MessageUpdate message);

    @PreAuthorize("canEdit(#key)")
    void delete(String key);

    Message get(String key);

}
