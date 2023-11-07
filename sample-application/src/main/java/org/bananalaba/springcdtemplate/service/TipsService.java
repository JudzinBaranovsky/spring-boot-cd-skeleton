package org.bananalaba.springcdtemplate.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bananalaba.springcdtemplate.client.TipsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TipsService {

    @NonNull
    private final TipsClient client;
    @NonNull
    @Value("${service.tips.default}")
    private final String defaultTip;

    @NonNull
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    public String getTip() {
        return circuitBreakerFactory.create("tips-service")
            .run(
                client::getTipOfTheDay,
                e -> {
                    log.error("failed to call tips-service", e);
                    return defaultTip;
                }
            );
    }

}
