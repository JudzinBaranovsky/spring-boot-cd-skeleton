package org.bananalaba.springcdtemplate.config;

import io.micrometer.observation.Observation.Context;
import io.micrometer.observation.ObservationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObservabilityConfig {

    @Bean
    public ObservationRegistryCustomizer<?> loggingObservationCustomizer() {
        return registry -> registry.observationConfig()
            .observationHandler(new ErrorLoggingObservationHandler());
    }


    @Slf4j
    private static class ErrorLoggingObservationHandler implements ObservationHandler<Context> {

        @Override
        public boolean supportsContext(Context context) {
            return true;
        }

        @Override
        public void onError(Context context) {
            log.error("unhandled error", context.getError());
        }

    }
}
