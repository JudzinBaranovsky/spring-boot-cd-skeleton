package org.bananalaba.teamsports.ingest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeamMatchHistoryIngestor {

    @NonNull
    private final SoccerApiClient apiClient;
    @NonNull
    private final TeamMatchHistoryStorage storage;

    public void ingest(final int year) {
        var history = apiClient.getMatchHistory(year);
        storage.save(history);
    }

}
