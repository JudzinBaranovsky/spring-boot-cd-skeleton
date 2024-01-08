package org.bananalaba.springcdtemplate.config;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain messageApiSecurityChain(HttpSecurity security) throws Exception {
        return security.authorizeHttpRequests(configurer -> configurer
                .requestMatchers(GET, "/api/v1/messages/**").hasAuthority("SCOPE_read:message")
                .requestMatchers(DELETE, "api/v1/messages/**").hasAuthority("SCOPE_delete:message")
                .requestMatchers(POST, "api/v1/messages/**").hasAuthority("SCOPE_write:message")
            )
            .oauth2ResourceServer(server -> server.jwt(jwt -> {}))
            .build();
    }

}
