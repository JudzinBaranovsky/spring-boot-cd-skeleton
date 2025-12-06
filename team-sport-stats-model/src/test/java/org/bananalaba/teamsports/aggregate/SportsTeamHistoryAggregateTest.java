package org.bananalaba.teamsports.aggregate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class SportsTeamHistoryAggregateTest {

    @Test
    void validAggregateMetricShouldBeCreated() {
        var metric = SportsTeamHistoryAggregate.AggregateMetric.builder()
                .team("TeamA")
                .amount(5.5)
                .build();
        assertThat(metric.getTeam()).isEqualTo("TeamA");
        assertThat(metric.getAmount()).isEqualTo(5.5);
    }

    @Test
    void invalidTeamNameThrows() {
        assertThatThrownBy(() -> SportsTeamHistoryAggregate.AggregateMetric.builder()
                .team("Invalid@Name!")
                .amount(1)
                .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("invalid team name");
    }

    @Test
    void teamNameLongerThan100Throws() {
        var longName = "T".repeat(101);
        assertThatThrownBy(() -> SportsTeamHistoryAggregate.AggregateMetric.builder()
                .team(longName)
                .amount(1)
                .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("invalid team name");
    }

    @Test
    void nullTeamThrows() {
        assertThatThrownBy(() -> SportsTeamHistoryAggregate.AggregateMetric.builder()
                .team(null)
                .amount(1)
                .build())
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void emptyMetricReturnsNoData() {
        var empty = SportsTeamHistoryAggregate.AggregateMetric.empty();
        assertThat(empty.getTeam()).isEqualTo("<no-data>");
        assertThat(empty.getAmount()).isEqualTo(0);
    }

    @Test
    void validSportsTeamHistoryAggregateShouldBeCreated() {
        var win = SportsTeamHistoryAggregate.AggregateMetric.builder().team("TeamA").amount(10).build();
        var scored = SportsTeamHistoryAggregate.AggregateMetric.builder().team("TeamB").amount(20).build();
        var received = SportsTeamHistoryAggregate.AggregateMetric.builder().team("TeamC").amount(5).build();
        var agg = SportsTeamHistoryAggregate.builder()
                .mostWin(win)
                .mostScoredPerGame(scored)
                .leastReceivedPerGame(received)
                .build();
        assertThat(agg.getMostWin()).isEqualTo(win);
        assertThat(agg.getMostScoredPerGame()).isEqualTo(scored);
        assertThat(agg.getLeastReceivedPerGame()).isEqualTo(received);
    }

    @Test
    void nullAggregateMetricThrows() {
        var win = SportsTeamHistoryAggregate.AggregateMetric.builder().team("TeamA").amount(10).build();
        var scored = SportsTeamHistoryAggregate.AggregateMetric.builder().team("TeamB").amount(20).build();
        assertThatThrownBy(() -> SportsTeamHistoryAggregate.builder()
                .mostWin(win)
                .mostScoredPerGame(scored)
                .leastReceivedPerGame(null)
                .build())
            .isInstanceOf(NullPointerException.class);
    }

}
