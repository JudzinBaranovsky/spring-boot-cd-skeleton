package org.bananalaba.springcdtemplate.config;

import lombok.extern.slf4j.Slf4j;
import org.bananalaba.springcdtemplate.service.DocumentOrderService;
import org.bananalaba.springcdtemplate.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoOperations;

@Configuration
@Profile("mongodb")
@Slf4j
public class MongoDbStorageConfig {

    @Bean
    public OrderService orderService() {
        return new DocumentOrderService();
    }

    @Autowired
    public void makeTestDbCall(final MongoOperations dbOps) {
        dbOps.createCollection("test-items");
        var result = dbOps.collectionExists("test-items");
        log.info("test MongoDB collection created: {}", result);
    }

}
