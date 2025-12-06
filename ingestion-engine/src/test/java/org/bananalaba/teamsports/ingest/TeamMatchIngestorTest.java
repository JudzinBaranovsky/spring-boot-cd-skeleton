package org.bananalaba.teamsports.ingest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.time.LocalDate;
import java.util.List;

import org.bananalaba.springcdtemplate.model.TeamMatch;
import org.bananalaba.springcdtemplate.model.TeamMatch.TournamentType;
import org.bananalaba.teamsports.AbstractTest;
import org.bananalaba.teamsports.TestDbToolkit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;

public class TeamMatchIngestorTest extends AbstractTest {

    private final TeamMatchHistoryIngestor ingestor;
    private final TestDbToolkit testDbToolkit;

    @Autowired
    public TeamMatchIngestorTest(TeamMatchHistoryIngestor ingestor, TestDbToolkit testDbToolkit) {
        this.ingestor = ingestor;
        this.testDbToolkit = testDbToolkit;
    }

    @BeforeEach
    void reset() {
        testDbToolkit.createTables();
        testDbToolkit.clear();
    }

    @Test
    void shouldIngestJsonFromRestApi(final MockServerClient mockServer) {
        mockServer.when(
            request()
                .withMethod("GET")
                .withPath("/soccer/2000")
            )
            .respond(
                response()
                    .withStatusCode(200)
                    .withBody("""
                        [
                            {
                              "date": "2000-12-29",
                              "country": "Spain",
                              "homeScore": 3,
                              "awayTeam": "Morocco",
                              "awayScore": 2,
                              "city": "Bilbao",
                              "homeTeam": "Basque Country",
                              "tournament": "Friendly"
                            },
                            {
                              "date": "2000-12-30",
                              "country": "France",
                              "homeScore": 1,
                              "awayTeam": "TeamA",
                              "awayScore": 0,
                              "city": "Paris",
                              "homeTeam": "TeamB",
                              "tournament": "Friendly"
                            },
                            {
                              "date": "2001-12-29",
                              "country": "Spain",
                              "homeScore": 7,
                              "awayTeam": "Morocco",
                              "awayScore": 2,
                              "city": "Bilbao",
                              "homeTeam": "Basque Country",
                              "tournament": "Friendly"
                            }
                        ]
                        """)
            );

        ingestor.ingest(2000);
        ingestor.ingest(2000); // to check if upserts work

        var actual = testDbToolkit.findAllTeamMatches();
        var expected = List.of(
            TeamMatch.builder()
                .date(LocalDate.parse("2000-12-29"))
                .country("Spain")
                .homeScore(3)
                .awayTeam("Morocco")
                .awayScore(2)
                .city("Bilbao")
                .homeTeam("Basque Country")
                .tournament(TournamentType.Friendly)
                .build(),
            TeamMatch.builder()
                .date(LocalDate.parse("2000-12-30"))
                .country("France")
                .homeScore(1)
                .awayTeam("TeamA")
                .awayScore(0)
                .city("Paris")
                .homeTeam("TeamB")
                .tournament(TournamentType.Friendly)
                .build(),
            TeamMatch.builder()
                .date(LocalDate.parse("2001-12-29"))
                .country("Spain")
                .homeScore(7)
                .awayTeam("Morocco")
                .awayScore(2)
                .city("Bilbao")
                .homeTeam("Basque Country")
                .tournament(TournamentType.Friendly)
                .build()
        );
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().containsExactlyInAnyOrderElementsOf(expected);
    }

}
