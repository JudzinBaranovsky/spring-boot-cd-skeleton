package org.bananalaba.springcdtemplate.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.teamsports.ingest.TeamMatchHistoryIngestor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class YearlyHistoryIngestionPipeline {

    @NonNull
    private final TeamMatchHistoryIngestor ingestor;

    public void run(final int year) {
        ingestor.ingest(year);
    }
}
