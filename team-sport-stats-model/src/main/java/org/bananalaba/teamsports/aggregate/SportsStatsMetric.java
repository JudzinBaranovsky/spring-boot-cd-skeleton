package org.bananalaba.teamsports.aggregate;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.bananalaba.teamsports.aggregate.ValidationAssets.isValidName;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
@EqualsAndHashCode
public class SportsStatsMetric {

    private final String key;
    private final String team;
    private final double value;

    @Builder
    public SportsStatsMetric(@NonNull String key, @NonNull String team, double value) {
        isTrue(isValidName(key), "Invalid key format: " + key);
        this.key = key;

        isTrue(isValidName(team), "Invalid team format: " + team);
        this.team = team;

        this.value = value;
    }
}
