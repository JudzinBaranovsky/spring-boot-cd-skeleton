package org.bananalaba.springcdtemplate.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("prod")
@PropertySource("file:${secrets.postgres.path}")
@Slf4j
public class ProdConfig {

    @Bean
    public JdbcConnectionDetails jdbcConnectionDetails(@Value("${JDBC_URL}") final String url,
                                                       @Value("${JDBC_USER_NAME}") final String username,
                                                       @Value("${JDBC_PASSWORD}") final String password) {
        log.info("Configuring production database connection details from secrets");
        return new JdbcConnectionDetails() {

            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public String getPassword() {
                return password;
            }

            @Override
            public String getJdbcUrl() {
                return url;
            }

        };
    }

}
