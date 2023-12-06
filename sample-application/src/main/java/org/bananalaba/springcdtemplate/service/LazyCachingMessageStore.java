package org.bananalaba.springcdtemplate.service;

import static org.bananalaba.springcdtemplate.config.CacheConfig.MESSAGE_CACHE_NAME;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LazyCachingMessageStore implements MessageStore {

    @NonNull
    @Qualifier("inMemoryMessageStore")
    private final MessageStore delegate;

    @Override
    public void put(String key, String value) {
        delegate.put(key, value);
    }

    @Override
    @Cacheable(MESSAGE_CACHE_NAME)
    public String get(String key) {
        return delegate.get(key);
    }

}
