package org.bananalaba.springcdtemplate.proxy.web;


import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;
import static org.springframework.security.oauth2.client.web.client.RequestAttributePrincipalResolver.principal;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.model.SampleDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/api/v1/proxy/status")
@RequiredArgsConstructor
public class ProxyController {

    @NonNull
    private final RestClient restClient;

    @GetMapping(produces = "application/json")
    public SampleDto getStatus() {
        return restClient.get()
            .uri("http://localhost:8080/api/v1/status")
            .attributes(clientRegistrationId("status-api-client"))
            .retrieve()
            .body(SampleDto.class);
    }

}
