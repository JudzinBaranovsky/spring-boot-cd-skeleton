package org.bananalaba.springcdtemplate.config;

import java.util.Map;

import org.bananalaba.springcdtemplate.data.MessageRepository;
import org.bananalaba.springcdtemplate.security.AclManager;
import org.bananalaba.springcdtemplate.security.AclRepository;
import org.bananalaba.springcdtemplate.security.spring.AccessControlExpressionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public static MethodSecurityExpressionHandler methodSecurityExpressionHandler(final AclManager aclManager) {
        return new AccessControlExpressionHandler(aclManager);
    }

    @Bean
    public static AclManager aclManager(final MessageRepository messageRepository, final AclRepository aclRepository) {
        return new AclManager(aclRepository, Map.of("message", messageRepository));
    }

}
