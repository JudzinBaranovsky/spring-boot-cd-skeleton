package org.bananalaba.springcdtemplate.config;

import static org.springframework.security.config.Customizer.withDefaults;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Slf4j
public class SecurityConfig {

    @Bean
    @Profile("s2s-auth")
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        log.info("initializing security filter chain with s2s-auth profile");
        return http
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/error").permitAll()
                .requestMatchers("/api/v1/**").hasAuthority("SCOPE_use:api")
            )
            .cors(withDefaults())
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(withDefaults())
            )
            .build();
    }

    @Bean
    @Profile("!s2s-auth")
    public SecurityFilterChain filterChainNoAuth(final HttpSecurity http) throws Exception {
        log.info("initializing security filter chain without authorisation");
        return http
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/**").permitAll()
            )
            .cors(withDefaults())
            .build();
    }

}
