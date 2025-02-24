package org.bananalaba.springcdtemplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain messageApiSecurityChain(final HttpSecurity security) throws Exception {
        return security.requiresChannel(configurer -> configurer.anyRequest().requiresSecure())
            .authorizeHttpRequests(configurer -> configurer.anyRequest().permitAll())
            .build();
    }

}
