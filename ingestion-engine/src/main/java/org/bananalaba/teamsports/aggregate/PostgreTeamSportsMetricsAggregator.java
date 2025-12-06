package org.bananalaba.teamsports.aggregate;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.teamsports.aggregate.SportsTeamHistoryAggregate.AggregateMetric;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PostgreTeamSportsMetricsAggregator implements TeamSportsMetricsAggregator {

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
    }

    private void precomputeAverageScoreByTeams() {
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
    }

    private void precomputeReceivedAverageByTeams() {
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
    }

    @Override
    @Transactional
    public SportsTeamHistoryAggregate aggregateAll() {
        var mostWin = jdbc.queryForObject("""
                select "team", sum("value") as "amount"
                    from team_metrics
                    where "key" = 'victoryCount'
                    group by "team"
                    order by "amount" desc limit 1
                """,
            new MetricTupleRowMapper()
        );
        var mostScoredPerGame = jdbc.queryForObject("""
                select "team", sum("value") as "amount"
                    from team_metrics
                    where "key" = 'averageScore'
                    group by "team"
                    order by "amount" desc limit 1
                """,
            new MetricTupleRowMapper()
        );
        var leastReceivedPerGame = jdbc.queryForObject("""
                select "team", sum("value") as "amount"
                  from team_metrics
                  where "key" = 'averageReceived'
                  group by "team"
                  order by "amount" asc limit 1
                """,
            new MetricTupleRowMapper()
        );

        return new SportsTeamHistoryAggregate(mostWin, mostScoredPerGame, leastReceivedPerGame);
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
