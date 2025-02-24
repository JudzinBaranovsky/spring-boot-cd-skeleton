package org.bananalaba.springcdtemplate.web;

import org.springframework.boot.ssl.SslBundles;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TestClientConfig {

    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder builder, final SslBundles sslBundles) {
       return builder.setSslBundle(sslBundles.getBundle("test-bundle")).build();
    }

}
