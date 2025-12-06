package org.bananalaba.springcdtemplate.model;

import static org.apache.commons.lang3.Validate.isTrue;

import java.time.LocalDate;
import java.util.regex.Pattern;

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
@ToString
public class TeamMatch {

    private static final Pattern VALID_NAME_PATTERN = Pattern.compile("[a-zA-Z\\-\\s_0-9]{1,100}");

    private final String awayTeam;
    private final String homeTeam;
    private final LocalDate date;

    private final String country;
    private final String city;

    private final String tournament;

    private final int homeScore;
    private final int awayScore;

    @Builder
    public TeamMatch(@NonNull String awayTeam,
                     @NonNull String homeTeam,
                     @NonNull LocalDate date,
                     @NonNull String country,
                     @NonNull String city,
                     @NonNull String tournament,
                     int homeScore,
                     int awayScore) {
        isTrue(homeScore >= 0, "home score cannot be < 0");
        isTrue(awayScore >= 0, "away score cannot be < 0");

        isTrue(!homeTeam.equalsIgnoreCase(awayTeam), "home and away teams cannot be the same");

        isTrue(isValidName(awayTeam), "invalid awayTeam");
        this.awayTeam = awayTeam;

        isTrue(isValidName(homeTeam), "invalid homeTeam");
        this.homeTeam = homeTeam;

        this.date = date;

        isTrue(isValidName(country), "invalid country");
        this.country = country;

        isTrue(isValidName(city), "invalid city");
        this.city = city;

        isTrue(isValidName(tournament), "invalid tournament");
        this.tournament = tournament;

        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    private boolean isValidName(final String name) {
        var matcher = VALID_NAME_PATTERN.matcher(name);
        return matcher.matches();
    }

}
