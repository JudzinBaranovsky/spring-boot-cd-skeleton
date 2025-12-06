package org.bananalaba.teamsports;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan
public class DataEngineConfig {

    @Bean("dataSourceRestTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
