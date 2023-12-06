package org.bananalaba.springcdtemplate.service;

import static org.bananalaba.springcdtemplate.config.CacheConfig.MESSAGE_CACHE_NAME;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EagerCachingMessageStore implements MessageStore {

    @NonNull
    @Qualifier("inMemoryMessageStore")
    private final MessageStore delegate;

    @Override
    public void put(String key, String value) {
        delegate.put(key, value);
    }

    @Override
    @CachePut(MESSAGE_CACHE_NAME)
    public String get(String key) {
        return delegate.get(key);
    }

}
