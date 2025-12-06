package org.bananalaba.teamsports.aggregate;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
@EqualsAndHashCode
public class SportsTeamHistoryAggregate {

    @NonNull
    private final AggregateMetric mostWin;
    @NonNull
    private final AggregateMetric mostScoredPerGame;
    @NonNull
    private final AggregateMetric lessReceivedPerGame;

    @Getter
    @Jacksonized
    @Builder
    @EqualsAndHashCode
    public static class AggregateMetric {

        @NonNull
        private final String team;
        private final double amount;

    }

}
