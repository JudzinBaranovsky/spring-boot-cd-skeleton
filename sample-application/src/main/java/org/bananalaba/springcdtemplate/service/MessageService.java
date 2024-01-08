package org.bananalaba.springcdtemplate.service;

import org.bananalaba.springcdtemplate.data.Message;

public interface MessageService {

    void save(String key, Message message);

    void delete(String key);

    Message get(String key);

}
