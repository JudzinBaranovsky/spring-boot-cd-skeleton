package org.bananalaba.springcdtemplate.service;

import org.bananalaba.springcdtemplate.data.Message;
import org.springframework.security.access.prepost.PreAuthorize;

public interface MessageService {

    @PreAuthorize("@aclManager.checkOrCaptureOwnership(#key)")
    void save(String key, Message message);

    @PreAuthorize("@aclManager.checkOwnership(#key)")
    void delete(String key);

    Message get(String key);

}
