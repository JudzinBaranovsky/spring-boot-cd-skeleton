package org.bananalaba.springcdtemplate.config;

import org.bananalaba.springcdtemplate.service.DocumentOrderService;
import org.bananalaba.springcdtemplate.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("mongodb")
public class MongoDbStorageConfig {

    @Bean
    public OrderService orderService() {
        return new DocumentOrderService();
    }

}
