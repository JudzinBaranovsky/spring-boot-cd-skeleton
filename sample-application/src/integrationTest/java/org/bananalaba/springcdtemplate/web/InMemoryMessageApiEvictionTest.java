package org.bananalaba.springcdtemplate.web;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
    properties = {
        "node.ip=192.168.0.1",
        "message.cache.expireAfterWriteMs=1000",
        "message.cache.type=in_memory"
    }
)
public class InMemoryMessageApiEvictionTest extends MessageApiEvictionTest {
}
