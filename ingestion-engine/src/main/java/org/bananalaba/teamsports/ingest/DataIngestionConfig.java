package org.bananalaba.teamsports.ingest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DataIngestionConfig {

    @Bean("dataSourceRestTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
