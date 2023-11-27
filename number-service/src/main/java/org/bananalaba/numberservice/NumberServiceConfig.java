package org.bananalaba.numberservice;

import java.util.Random;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.bananalaba.numberservice.service.NumberGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NumberServiceConfig {

    @Bean
    @SuppressFBWarnings(value = {"PREDICTABLE_RANDOM"}, justification = "these random values are not involved into any secure flows")
    public NumberGenerator numberGenerator(@Value("${numberService.generator.maxResponseLatency}") int maxResponseLatency) {
        return new NumberGenerator(new Random(), maxResponseLatency);
    }

    @Bean
    public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }

}
