package org.bananalaba.springcdtemplate.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TeamMatchTest {

    private static final LocalDate DATE = LocalDate.of(2023, 1, 1);

    @Test
    void validTeamMatchShouldBeCreated() {
        var match = TeamMatch.builder()
                .homeTeam("HomeTeam")
                .awayTeam("AwayTeam")
                .date(DATE)
                .country("Country")
                .city("City")
                .tournament("Tournament")
                .homeScore(1)
                .awayScore(2)
                .build();
        assertThat(match.getHomeTeam()).isEqualTo("HomeTeam");
        assertThat(match.getAwayTeam()).isEqualTo("AwayTeam");
        assertThat(match.getHomeScore()).isEqualTo(1);
        assertThat(match.getAwayScore()).isEqualTo(2);
    }

    @Test
    void supportsNamesWithAccentedOrUnicodeLetters() {
        var match = TeamMatch.builder()
            .homeTeam("Équipe")
            .awayTeam("München")
            .date(DATE)
            .country("Côte d’Ivoire")
            .city("Lomé")
            .tournament("Copa São Paulo")
            .homeScore(3)
            .awayScore(1)
            .build();
        assertThat(match.getHomeTeam()).isEqualTo("Équipe");
        assertThat(match.getAwayTeam()).isEqualTo("München");
        assertThat(match.getCountry()).isEqualTo("Côte d’Ivoire");
        assertThat(match.getCity()).isEqualTo("Lomé");
        assertThat(match.getTournament()).isEqualTo("Copa São Paulo");
    }

    @Test
    void homeScoreCannotBeNegative() {
        assertThatThrownBy(() -> TeamMatch.builder()
                .homeTeam("HomeTeam")
                .awayTeam("AwayTeam")
                .date(DATE)
                .country("Country")
                .city("City")
                .tournament("Tournament")
                .homeScore(-1)
                .awayScore(2)
                .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("home score cannot be < 0");
    }

    @Test
    void awayScoreCannotBeNegative() {
        assertThatThrownBy(() -> TeamMatch.builder()
                .homeTeam("HomeTeam")
                .awayTeam("AwayTeam")
                .date(DATE)
                .country("Country")
                .city("City")
                .tournament("Tournament")
                .homeScore(1)
                .awayScore(-2)
                .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("away score cannot be < 0");
    }

    @Test
    void homeAndAwayTeamsCannotBeSame() {
        assertThatThrownBy(() -> TeamMatch.builder()
                .homeTeam("Team")
                .awayTeam("Team")
                .date(DATE)
                .country("Country")
                .city("City")
                .tournament("Tournament")
                .homeScore(1)
                .awayScore(2)
                .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("home and away teams cannot be the same");
    }

    @Test
    void homeAndAwayTeamsCannotBeSameCaseInsensitive() {
        assertThatThrownBy(() -> TeamMatch.builder()
                .homeTeam("Team")
                .awayTeam("team")
                .date(DATE)
                .country("Country")
                .city("City")
                .tournament("Tournament")
                .homeScore(1)
                .awayScore(2)
                .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("home and away teams cannot be the same");
    }

    @Test
    void invalidAwayTeamNameThrows() {
        assertThatThrownBy(() -> TeamMatch.builder()
                .homeTeam("HomeTeam")
                .awayTeam("Invalid@Name")
                .date(DATE)
                .country("Country")
                .city("City")
                .tournament("Tournament")
                .homeScore(1)
                .awayScore(2)
                .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("invalid awayTeam");
    }

    @Test
    void invalidHomeTeamNameThrows() {
        assertThatThrownBy(() -> TeamMatch.builder()
                .homeTeam("Invalid@Name")
                .awayTeam("AwayTeam")
                .date(DATE)
                .country("Country")
                .city("City")
                .tournament("Tournament")
                .homeScore(1)
                .awayScore(2)
                .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("invalid homeTeam");
    }

    @Test
    void invalidCountryNameThrows() {
        assertThatThrownBy(() -> TeamMatch.builder()
                .homeTeam("HomeTeam")
                .awayTeam("AwayTeam")
                .date(DATE)
                .country("Invalid@Country")
                .city("City")
                .tournament("Tournament")
                .homeScore(1)
                .awayScore(2)
                .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("invalid country");
    }

    @Test
    void invalidCityNameThrows() {
        assertThatThrownBy(() -> TeamMatch.builder()
                .homeTeam("HomeTeam")
                .awayTeam("AwayTeam")
                .date(DATE)
                .country("Country")
                .city("Invalid@City")
                .tournament("Tournament")
                .homeScore(1)
                .awayScore(2)
                .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("invalid city");
    }

    @Test
    void invalidTournamentNameThrows() {
        assertThatThrownBy(() -> TeamMatch.builder()
                .homeTeam("HomeTeam")
                .awayTeam("AwayTeam")
                .date(DATE)
                .country("Country")
                .city("City")
                .tournament("Invalid@Tournament")
                .homeScore(1)
                .awayScore(2)
                .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("invalid tournament");
    }

    @Test
    void nameLongerThan100CharactersThrows() {
        var longName = "A".repeat(101);
        assertThatThrownBy(() -> TeamMatch.builder()
                .homeTeam(longName)
                .awayTeam("AwayTeam")
                .date(DATE)
                .country("Country")
                .city("City")
                .tournament("Tournament")
                .homeScore(1)
                .awayScore(2)
                .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("invalid homeTeam");

        assertThatThrownBy(() -> TeamMatch.builder()
                .homeTeam("HomeTeam")
                .awayTeam(longName)
                .date(DATE)
                .country("Country")
                .city("City")
                .tournament("Tournament")
                .homeScore(1)
                .awayScore(2)
                .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("invalid awayTeam");

        assertThatThrownBy(() -> TeamMatch.builder()
                .homeTeam("HomeTeam")
                .awayTeam("AwayTeam")
                .date(DATE)
                .country(longName)
                .city("City")
                .tournament("Tournament")
                .homeScore(1)
                .awayScore(2)
                .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("invalid country");

        assertThatThrownBy(() -> TeamMatch.builder()
                .homeTeam("HomeTeam")
                .awayTeam("AwayTeam")
                .date(DATE)
                .country("Country")
                .city(longName)
                .tournament("Tournament")
                .homeScore(1)
                .awayScore(2)
                .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("invalid city");

        assertThatThrownBy(() -> TeamMatch.builder()
                .homeTeam("HomeTeam")
                .awayTeam("AwayTeam")
                .date(DATE)
                .country("Country")
                .city("City")
                .tournament(longName)
                .homeScore(1)
                .awayScore(2)
                .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("invalid tournament");
    }

}
