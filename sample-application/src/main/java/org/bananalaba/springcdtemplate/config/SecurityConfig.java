package org.bananalaba.springcdtemplate.config;

import org.bananalaba.springcdtemplate.security.spring.AccessControlExpressionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public static MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        return new AccessControlExpressionHandler();
    }

}
