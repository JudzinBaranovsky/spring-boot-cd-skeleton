package org.bananalaba.teamsports.ingest;

import java.util.List;
import java.util.function.Consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bananalaba.springcdtemplate.model.TeamMatch;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class SoccerApiClient {

    private static final TypeReference<List<TeamMatch>> MATCH_LIST_TYPE = new TypeReference<>() {};

    @NonNull
    @Value("${soccer.api.baseUrl}")
    private final String sourceApiBaseUrl;
    @NonNull
    @Qualifier("dataSourceRestTemplate")
    private final RestTemplate restClient;
    @NonNull
    private final MatchHistoryJsonStreamMapper jsonMapper;

    public void getMatchHistory(final int year, final Consumer<List<TeamMatch>> batchConsumer) {
        log.info("fetching match history by {} year from {}", year, sourceApiBaseUrl);

        var url = sourceApiBaseUrl + "/soccer/" + year;
        restClient.execute(url, HttpMethod.GET, __ -> {}, response -> {
            if (response.getStatusCode() == HttpStatus.OK) {
                var result = jsonMapper.map(response.getBody());
                result.forEach(batchConsumer);

                return null;
            }

            throw new DataIngestionException("unexpected status code: " + response.getStatusCode().value());
        });
    }

}
