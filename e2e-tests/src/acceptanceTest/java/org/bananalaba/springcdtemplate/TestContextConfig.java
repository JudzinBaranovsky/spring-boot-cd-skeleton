package org.bananalaba.springcdtemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("classpath:/${env:local}-environment.properties")
public class TestContextConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
