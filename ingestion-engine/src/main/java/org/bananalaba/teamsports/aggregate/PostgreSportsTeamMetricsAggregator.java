package org.bananalaba.teamsports.aggregate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bananalaba.teamsports.aggregate.SportsTeamHistoryAggregate.AggregateMetric;
import org.bananalaba.teamsports.ingest.DataSourceException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostgreSportsTeamMetricsAggregator implements SportsTeamMetricsAggregator {

    @NonNull
    private JdbcOperations jdbc;

    @Override
    @Transactional
    public void precomputeMetrics() {
        precomputeWinsByTeams();
        precomputeAverageScoreByTeams();
        precomputeReceivedAverageByTeams();
    }

    private void precomputeWinsByTeams() {
        log.info("precomputing wins by teams");
        jdbc.execute("""
            insert into team_metrics ("key", "team", "value")
            select
              'victoryCount' as "key",
              case
                when "homeScore" > "awayScore" then "homeTeam"
                when "awayScore" > "homeScore" then "awayTeam"
                else null
              end as "team",
              count(*) as "value"
            from team_match_history
            where "homeScore" <> "awayScore"
            group by "team"
            on conflict ("key", "team") do update
            set "value" = excluded."value";
            """);

        log.info("precomputing wins by teams - done");
    }

    private void precomputeAverageScoreByTeams() {
        log.info("precomputing average score teams");
        jdbc.execute("""
            insert into team_metrics ("key", "team", "value")
            select
              'averageScore' as "key",
              team,
              avg(score) as "value"
            from (
                select "homeTeam" as "team", "homeScore" as score from team_match_history
                union all
                select "awayTeam" as "team", "awayScore" as score from team_match_history
            ) as all_scores
             group by "team"
             on conflict ("key", "team") do update
             set "value" = excluded."value";
            """
        );
        log.info("precomputing average score teams - done");
    }

    private void precomputeReceivedAverageByTeams() {
        log.info("precomputing received average teams");
        jdbc.execute("""
            insert into team_metrics ("key", "team", "value")
            select
              'averageReceived' as "key",
              team,
              avg(received) as "value"
            from (
                select "homeTeam" as "team", "awayScore" as received from team_match_history
                union all
                select "awayTeam" as "team", "homeScore" as received from team_match_history
            ) as all_received
             group by "team"
             on conflict ("key", "team") do update
             set "value" = excluded."value";
            """
        );
        log.info("precomputing received average teams - done");
    }

    @Override
    @Transactional
    public SportsTeamHistoryAggregate aggregateAll() {
        log.info("aggregating all metrics");
        var mostWin = getMetric("""
                select "team", sum("value") as "amount"
                    from team_metrics
                    where "key" = 'victoryCount'
                    group by "team"
                    order by "amount" desc limit 1
                """);
        var mostScoredPerGame = getMetric("""
                select "team", sum("value") as "amount"
                    from team_metrics
                    where "key" = 'averageScore'
                    group by "team"
                    order by "amount" desc limit 1
                """);
        var leastReceivedPerGame = getMetric("""
                select "team", sum("value") as "amount"
                  from team_metrics
                  where "key" = 'averageReceived'
                  group by "team"
                  order by "amount" asc limit 1
                """);

        return new SportsTeamHistoryAggregate(mostWin, mostScoredPerGame, leastReceivedPerGame);
    }

    private AggregateMetric getMetric(final String sql) {
        var result = jdbc.query(sql, new MetricTupleRowMapper());
        if (result.size() > 1) {
            throw new DataSourceException("unexpected aggregation result: got more than one metric value for SQL: " + sql);
        }

        log.debug("aggregate results by sql {}: {}", sql, result);
        return result.isEmpty() ? AggregateMetric.empty() : result.getFirst();
    }

    private static class MetricTupleRowMapper implements RowMapper<AggregateMetric> {

        @Override
        public AggregateMetric mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            return new AggregateMetric(
                rs.getString("team"),
                rs.getDouble("amount")
            );
        }

    }

}
