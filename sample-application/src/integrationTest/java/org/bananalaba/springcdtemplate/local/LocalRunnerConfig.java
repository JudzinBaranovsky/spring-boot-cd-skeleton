package org.bananalaba.springcdtemplate.local;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@Configuration
public class LocalRunnerConfig {

    @Bean
    @ServiceConnection
    @Profile("postgresql")
    public PostgreSQLContainer<?> postgreSqlContainer() {
        return new PostgreSQLContainer<>("postgres:14-alpine");
    }

    @Bean
    @ServiceConnection
    @Profile("mongodb")
    public MongoDBContainer mongoDbContainer() {
        return new MongoDBContainer("mongo:7.0");
    }

}
