package org.bananalaba.springcdtemplate.web;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bananalaba.springcdtemplate.logging.Loggable;
import org.bananalaba.springcdtemplate.service.IngestionPipeline;
import org.bananalaba.teamsports.aggregate.SportsTeamHistoryAggregate;
import org.bananalaba.teamsports.aggregate.SportsTeamMetricsAggregator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sports")
@Slf4j
@RequiredArgsConstructor
@Loggable
public class DataAggregationController {

    @NonNull
    private final IngestionPipeline ingestionPipeline;
    @NonNull
    private final SportsTeamMetricsAggregator aggregator;

    @GetMapping(path = "/aggregate", produces = "application/json")
    public SportsTeamHistoryAggregate aggregate() {
        log.info("received an aggregate request");
        return aggregator.aggregateAll();
    }

    @PostMapping(path = "/ingest")
    public void ingestData() {
        ingestionPipeline.run();
        aggregator.precomputeMetrics();
    }

}
