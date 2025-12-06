package org.bananalaba.teamsports;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan
public class DataEngineConfig {

    @Bean("dataSourceRestTemplate")
    public RestTemplate restTemplate(@Value("${soccer.api.connectionTimeoutMs:30000}") final int connectionTimeoutMs,
                                     @Value("${soccer.api.socketTimeoutMs:30000}")final int socketTimeoutMs) {
        var requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofMillis(connectionTimeoutMs));
        requestFactory.setReadTimeout(Duration.ofMillis(socketTimeoutMs));

        return new RestTemplate(requestFactory);
    }

}
