package org.bananalaba.springcdtemplate.web;

import com.redis.testcontainers.RedisContainer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(
    properties = {
        "node.ip=192.168.0.1",
        "message.cache.type=redis"
    }
)
@Testcontainers
public class RedisMessageApiTest extends MessageApiTest {

    @Container
    private static final RedisContainer REDIS_CONTAINER = new RedisContainer(RedisContainer.DEFAULT_IMAGE_NAME.withTag(RedisContainer.DEFAULT_TAG));

    @DynamicPropertySource
    public static void registerRedisProperties(DynamicPropertyRegistry registry) {
        registry.add("message.cache.redis.host", REDIS_CONTAINER::getHost);
        registry.add("message.cache.redis.port", () -> REDIS_CONTAINER.getMappedPort(6379).toString());
    }

}
