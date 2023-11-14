package org.bananalaba.springcdtemplate.client;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@Component
@RequiredArgsConstructor
public class NumberServiceClient {

    @NonNull
    @Value("${client.numberService.host}")
    private final String host;
    @NonNull
    private final RestOperations restTemplate;

    public Long getRandomNumber() {
        return restTemplate.getForObject(host + "/api/v1/numbers/random", Long.class);
    }

}
