package org.bananalaba.springcdtemplate.config;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.HEAD;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.Customizer.withDefaults;

import org.bananalaba.springcdtemplate.stereotype.WebComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebComponent {

    @Value("${web.cors.allowedOrigins}")
    private String corsAllowedOrigins;

    @Bean
    public SecurityFilterChain messageApiSecurityChain(HttpSecurity security) throws Exception {
        return security.authorizeHttpRequests(configurer -> configurer
                .requestMatchers(GET, "/api/v1/messages/**").hasAuthority("SCOPE_read:message")
                .requestMatchers(DELETE, "api/v1/messages/**").hasAuthority("SCOPE_delete:message")
                .requestMatchers(POST, "api/v1/messages/**").hasAuthority("SCOPE_write:message")
                .requestMatchers(OPTIONS, "api/v1/messages/**").permitAll()
                .requestMatchers(GET, "/error").permitAll()
            )
            .csrf(csrf -> csrf.ignoringRequestMatchers("/api/v1/**"))
            .oauth2ResourceServer(server -> server.jwt(withDefaults()))
            .build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/v1/**")
                    .allowedMethods(GET.name(), OPTIONS.name(), HEAD.name(), POST.name(), DELETE.name())
                    .allowedOrigins(corsAllowedOrigins);
            }

        };
    }

}
