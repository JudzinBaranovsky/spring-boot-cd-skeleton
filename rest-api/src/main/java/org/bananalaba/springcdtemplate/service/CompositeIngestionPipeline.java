package org.bananalaba.springcdtemplate.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bananalaba.teamsports.aggregate.SportsTeamMetricsAggregator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CompositeIngestionPipeline implements IngestionPipeline {

    @NonNull
    private final YearlyHistoryIngestionPipeline sourcePipeline;
    @NonNull
    private final SportsTeamMetricsAggregator aggregator;

    public void run() {
        log.info("running ingestion");

        sourcePipeline.run(2000);
        sourcePipeline.run(2001);

        log.info("running aggregation");
        aggregator.precomputeMetrics();

        log.info("pipeline complete");
    }

}
