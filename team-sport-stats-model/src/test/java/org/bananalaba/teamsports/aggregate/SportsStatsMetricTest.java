package org.bananalaba.teamsports.aggregate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class SportsStatsMetricTest {

    @Test
    void validMetricShouldBeCreated() {
        var metric = SportsStatsMetric.builder()
                .key("Goals")
                .team("TeamA")
                .value(10.5)
                .build();
        assertThat(metric.getKey()).isEqualTo("Goals");
        assertThat(metric.getTeam()).isEqualTo("TeamA");
        assertThat(metric.getValue()).isEqualTo(10.5);
    }

    @Test
    void invalidKeyFormatThrows() {
        assertThatThrownBy(() -> SportsStatsMetric.builder()
                .key("Invalid@Key!")
                .team("TeamA")
                .value(1)
                .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Invalid key format");
    }

    @Test
    void invalidTeamFormatThrows() {
        assertThatThrownBy(() -> SportsStatsMetric.builder()
                .key("Goals")
                .team("Team@A!")
                .value(1)
                .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Invalid team format");
    }

    @Test
    void keyLongerThan100Throws() {
        var longKey = "K".repeat(101);
        assertThatThrownBy(() -> SportsStatsMetric.builder()
                .key(longKey)
                .team("TeamA")
                .value(1)
                .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Invalid key format");
    }

    @Test
    void teamLongerThan100Throws() {
        var longTeam = "T".repeat(101);
        assertThatThrownBy(() -> SportsStatsMetric.builder()
                .key("Goals")
                .team(longTeam)
                .value(1)
                .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Invalid team format");
    }

    @Test
    void nullKeyThrows() {
        assertThatThrownBy(() -> SportsStatsMetric.builder()
                .key(null)
                .team("TeamA")
                .value(1)
                .build())
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void nullTeamThrows() {
        assertThatThrownBy(() -> SportsStatsMetric.builder()
                .key("Goals")
                .team(null)
                .value(1)
                .build())
            .isInstanceOf(NullPointerException.class);
    }

}

