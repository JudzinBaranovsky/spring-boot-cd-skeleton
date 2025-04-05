package org.bananalaba.springcdtemplate.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bananalaba.springcdtemplate.client.TipsClient;
import org.bananalaba.springcdtemplate.resilience.Resilient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TipsService {

    @NonNull
    private final TipsClient client;

    @Resilient(value = "tips-service", fallback = "${service.tips.default}")
    public String getTip() {
        return client.getTipOfTheDay();
    }

}
