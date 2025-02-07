package org.bananalaba.springcdtemplate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("prod")
@PropertySource("${secrets.postgres.path}")
public class PostgreProdConfig {

    @Bean
    public JdbcConnectionDetails jdbcConnectionDetails(@Value("${db.messages.url}") final String url,
                                                       @Value("${db.messages.username}") final String username,
                                                       @Value("${db.messages.password}") final String password) {
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
