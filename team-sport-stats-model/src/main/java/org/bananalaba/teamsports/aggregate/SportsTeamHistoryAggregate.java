package org.bananalaba.teamsports.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class SportsTeamHistoryAggregate {

    private static final AggregateMetric EMPTY_METRIC = new AggregateMetric("<no-data>", 0);

    @NonNull
    private final AggregateMetric mostWin;
    @NonNull
    private final AggregateMetric mostScoredPerGame;
    @NonNull
    private final AggregateMetric leastReceivedPerGame;



    @Getter
    @Jacksonized
    @Builder
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class AggregateMetric {

        @NonNull
        private final String team;
        private final double amount;

        public static AggregateMetric empty() {
            return EMPTY_METRIC;
        }

    }

}
