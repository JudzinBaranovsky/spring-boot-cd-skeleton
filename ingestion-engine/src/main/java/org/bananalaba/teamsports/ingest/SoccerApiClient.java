package org.bananalaba.teamsports.ingest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper jsonMapper;

    public List<TeamMatch> getMatchHistory(final int year) {
        log.info("fetching match history by {} year from {}", year, sourceApiBaseUrl);

        var url = sourceApiBaseUrl + "/soccer/" + year;
        return restClient.execute(url, HttpMethod.GET, __ -> {}, response -> {
            if (response.getStatusCode() == HttpStatus.OK) {
                var result = mapAsMatches(response.getBody());
                log.debug("fetched match history by {} year from {}: {}", year, sourceApiBaseUrl, result);

                return result;
            }

            throw new DataIngestionException("unexpected status code: " + response.getStatusCode().value());
        });
    }

    private List<TeamMatch> mapAsMatches(final InputStream input) {
        try {
            return jsonMapper.readValue(input, MATCH_LIST_TYPE);
        } catch (IOException e) {
            throw new DataSourceException("failed to parse source data", e);
        }
    }

}
