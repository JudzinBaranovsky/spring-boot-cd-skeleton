package org.bananalaba.springcdtemplate.web;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class TestConfig {

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSqlContainer() {
        return new PostgreSQLContainer<>("postgres:14-alpine");
    }

}
