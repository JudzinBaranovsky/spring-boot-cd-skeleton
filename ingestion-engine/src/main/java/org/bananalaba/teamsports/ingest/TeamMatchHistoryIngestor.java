package org.bananalaba.teamsports.ingest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TeamMatchHistoryIngestor {

    @NonNull
    private final SoccerApiClient apiClient;
    @NonNull
    private final TeamMatchHistoryStorage storage;

    public void ingest(final int year) {
        log.info("ingesting history by year {}", year);

        apiClient.getMatchHistory(year, storage::save);

        log.info("ingesting history by year {} - done", year);
    }

}
