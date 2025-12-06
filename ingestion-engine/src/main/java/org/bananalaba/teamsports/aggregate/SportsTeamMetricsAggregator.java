package org.bananalaba.teamsports.aggregate;

public interface SportsTeamMetricsAggregator {

    void precomputeMetrics();

    SportsTeamHistoryAggregate aggregateAll();

}
