package org.bananalaba.springcdtemplate.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.teamsports.aggregate.SportsTeamMetricsAggregator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompositeIngestionPipeline implements IngestionPipeline {

    @NonNull
    private final YearlyHistoryIngestionPipeline sourcePipeline;
    @NonNull
    private final SportsTeamMetricsAggregator aggregator;

    public void run() {
        sourcePipeline.run(2000);
        sourcePipeline.run(2001);

        aggregator.precomputeMetrics();
    }

}
