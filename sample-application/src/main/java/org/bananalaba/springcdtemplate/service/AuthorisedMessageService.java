package org.bananalaba.springcdtemplate.service;

import static org.apache.commons.lang3.Validate.notNull;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.data.Message;
import org.bananalaba.springcdtemplate.data.MessageRepository;
import org.bananalaba.springcdtemplate.data.MessageUpdate;
import org.bananalaba.springcdtemplate.security.PrincipalContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorisedMessageService implements MessageService {

    @NonNull
    private final MessageRepository repository;
    @NonNull
    private final PrincipalContext principalContext;

    @Override
    public void save(String key, MessageUpdate update) {
        notNull(update, "update cannot be null");

        var principalId = principalContext.getCurrentPrincipalId();
        var message = Message.builder()
            .ownerId(principalId)
            .text(update.getText())
            .build();

        repository.save(key, message);
    }

    @Override
    public void delete(String key) {
        repository.delete(key);
    }

    @Override
    public Message get(String key) {
        var message = repository.get(key);
        if (message == null) {
            throw new IllegalArgumentException("message with key=" + key + " not found");
        }

        return message;
    }

}
