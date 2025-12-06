package org.bananalaba.teamsports.aggregate;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.bananalaba.teamsports.aggregate.ValidationAssets.isValidName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
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
    @ToString
    public static class AggregateMetric {

        private final String team;
        private final double amount;

        @Builder
        public AggregateMetric(@NonNull String team, double amount) {
            isTrue(isValidName(team), "invalid team name " + team);
            this.team = team;

            this.amount = amount;
        }

        public static AggregateMetric empty() {
            return EMPTY_METRIC;
        }

    }

}
