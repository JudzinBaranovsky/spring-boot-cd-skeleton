package org.bananalaba.perf.data.service;

import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataModule {

    @Bean
    public DataService dataService(@Value("${dataService.minLatency}") final long minLatency, @Value("${dataService.maxLatency}") final long maxLatency) {
        var random = new Random();
        Supplier<String> dataGenerator = () -> UUID.randomUUID().toString();
        return new DataService(minLatency, maxLatency, random, dataGenerator);
    }

}
