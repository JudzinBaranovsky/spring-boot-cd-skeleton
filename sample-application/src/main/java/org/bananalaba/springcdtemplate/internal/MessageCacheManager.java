package org.bananalaba.springcdtemplate.internal;

import static org.apache.commons.lang3.Validate.notNull;
import static org.bananalaba.springcdtemplate.config.CacheConfig.MESSAGE_CACHE_NAME;

import lombok.NonNull;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class MessageCacheManager {

    private final Cache cache;

    public MessageCacheManager(@NonNull CacheManager cacheManager) {
        cache = notNull(cacheManager.getCache(MESSAGE_CACHE_NAME), "message cache not configured");
    }

    public void cleanAll() {
        cache.clear();
    }

}
