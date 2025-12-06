package org.bananalaba.springcdtemplate.web;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.PostgreSQLContainer;

@Configuration
@ComponentScan
@Profile("docker")
public class LocalRunnerConfig {

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSqlContainer() {
        return new PostgreSQLContainer<>("postgres:14-alpine");
    }

}
