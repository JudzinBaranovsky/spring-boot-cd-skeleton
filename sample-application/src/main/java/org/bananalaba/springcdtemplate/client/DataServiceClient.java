package org.bananalaba.springcdtemplate.client;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.model.DataItemDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@Component
@RequiredArgsConstructor
public class DataServiceClient {

    @NonNull
    @Value("${client.dataService.host}")
    private final String host;
    @NonNull
    private final RestOperations restTemplate;

    public DataItemDto getData(@NonNull final String parameter) {
        return restTemplate.getForObject(host + "/api/v1/data/" + parameter, DataItemDto.class);
    }

}
