package org.bananalaba.springcdtemplate.proxy.service;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.model.SampleDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class StatusService {

    @Value("${api.status.baseUrl:http://localhost:8080/api/v1/status}")
    private final String statusApiBaseUrl;
    @Value("${api.status.clientId:status-api-client}")
    private final String statusApiClientId;
    @NonNull
    private final RestClient restClient;

    public SampleDto getStatus() {
        return restClient.get()
            .uri(statusApiBaseUrl)
            .attributes(clientRegistrationId(statusApiClientId))
            .retrieve()
            .body(SampleDto.class);
    }

}
