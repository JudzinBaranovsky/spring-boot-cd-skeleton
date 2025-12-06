package org.bananalaba.teamsports.aggregate;

public interface TeamSportsMetricsAggregator {

    void precomputeMetrics();

    SportsTeamHistoryAggregate aggregateAll();

}
