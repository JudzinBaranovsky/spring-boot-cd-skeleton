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
public class SportsStatsMetric {

    @NonNull
    private final String key;
    @NonNull
    private final String team;
    private final double value;

}
