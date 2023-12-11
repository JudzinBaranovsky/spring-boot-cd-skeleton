package org.bananalaba.springcdtemplate.config;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String MESSAGE_CACHE_NAME = "messages";

    @Bean
    @ConditionalOnProperty(name = "message.cache.type", havingValue = "in_memory")
    public CacheManager inMemoryCacheManager(@Value("${message.cache.expireAfterWriteMs}") int expireAfterWriteMs) {
        var backend = Caffeine.newBuilder()
            .expireAfterWrite(expireAfterWriteMs, TimeUnit.MILLISECONDS)
            .build();

        var manager = new SimpleCacheManager();
        manager.setCaches(List.of(new CaffeineCache(MESSAGE_CACHE_NAME, backend)));

        return manager;
    }

    @Bean
    @ConditionalOnProperty(name = "message.cache.type", havingValue = "redis")
    public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory,
                                          @Value("${message.cache.expireAfterWriteMs}") int expireAfterWriteMs) {
        return RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory)
            .withCacheConfiguration(
                MESSAGE_CACHE_NAME,
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMillis(expireAfterWriteMs))
            )
            .build();
    }

    @Bean
    @ConditionalOnProperty(name = "message.cache.type", havingValue = "redis")
    public RedisConnectionFactory messageCacheConnectionFactory(@Value("${message.cache.redis.host}") String host,
                                                                @Value("${message.cache.redis.port}") int port) {
        var config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);

        return new LettuceConnectionFactory(config);
    }

}
