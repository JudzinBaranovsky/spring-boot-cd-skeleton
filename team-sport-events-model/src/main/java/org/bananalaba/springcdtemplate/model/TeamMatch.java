package org.bananalaba.springcdtemplate.model;

import static org.apache.commons.lang3.Validate.isTrue;

import java.time.LocalDate;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
@EqualsAndHashCode
public class TeamMatch {

    private final String awayTeam;
    private final String homeTeam;
    private final LocalDate date;

    private final String country;
    private final String city;

    private final TournamentType tournament;

    private final int homeScore;
    private final int awayScore;

    @Builder
    public TeamMatch(@NonNull String awayTeam,
                     @NonNull String homeTeam,
                     @NonNull LocalDate date,
                     @NonNull String country,
                     @NonNull String city,
                     @NonNull TournamentType tournament,
                     int homeScore,
                     int awayScore) {
        isTrue(homeScore >= 0, "home score cannot be < 0");
        isTrue(awayScore >= 0, "away score cannot be < 0");

        isTrue(!homeTeam.equalsIgnoreCase(awayTeam), "home and away teams cannot be the same");

        this.awayTeam = awayTeam;
        this.homeTeam = homeTeam;
        this.date = date;
        this.country = country;
        this.city = city;
        this.tournament = tournament;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public enum TournamentType {
        Friendly
    }

}
