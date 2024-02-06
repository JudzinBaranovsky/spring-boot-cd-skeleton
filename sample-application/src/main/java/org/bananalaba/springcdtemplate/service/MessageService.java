package org.bananalaba.springcdtemplate.service;

import org.bananalaba.springcdtemplate.data.Message;
import org.bananalaba.springcdtemplate.data.MessageUpdate;
import org.springframework.security.access.prepost.PreAuthorize;

public interface MessageService {

    @PreAuthorize("isOwnedOrNew(#key)")
    void save(String key, MessageUpdate message);

    @PreAuthorize("isOwned(#key)")
    void delete(String key);

    Message get(String key);

}
