package org.bananalaba.teamsports.ingest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.time.LocalDate;
import java.util.List;

import org.bananalaba.springcdtemplate.model.TeamMatch;
import org.bananalaba.springcdtemplate.model.TeamMatch.TournamentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {8090})
@SpringBootTest
@ContextConfiguration(classes = LocalTestConfig.class)
@TestPropertySource(properties = {
    "soccer.api.baseUrl=http://localhost:8090"
})
public class TeamMatchIngestorTest {

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
                            }
                        ]
                        """)
            );

        ingestor.ingest(2000);

        var actual = testDbToolkit.findAllTeamMatches();
        var expected = List.of(TeamMatch.builder()
            .date(LocalDate.parse("2000-12-29"))
            .country("Spain")
            .homeScore(3)
            .awayTeam("Morocco")
            .awayScore(2)
            .city("Bilbao")
            .homeTeam("Basque Country")
            .tournament(TournamentType.Friendly)
            .build()
        );
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }

}
