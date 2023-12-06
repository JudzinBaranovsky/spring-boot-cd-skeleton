package org.bananalaba.springcdtemplate.config;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.util.concurrent.MoreExecutors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String MESSAGE_CACHE_NAME = "messages";

    @Bean
    public CacheManager cacheManager(@Value("${message.cache.expireAfterWriteMs}") int expireAfterWriteMs) {
        var messageCacheBackend = Caffeine.newBuilder()
            .expireAfterWrite(expireAfterWriteMs, TimeUnit.MILLISECONDS)
            .executor(MoreExecutors.directExecutor()) // for testing purposes
            .build();

        var manager = new SimpleCacheManager();
        manager.setCaches(List.of(
            new CaffeineCache(MESSAGE_CACHE_NAME, messageCacheBackend)
        ));

        return manager;
    }

}
