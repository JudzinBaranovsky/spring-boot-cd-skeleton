package org.bananalaba.teamsports.ingest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.bananalaba.springcdtemplate.model.TeamMatch;
import org.bananalaba.springcdtemplate.model.TeamMatch.TournamentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {8090})
public class TeamMatchIngestorTest {

    private final MockServerClient mockServer;
    private final TeamMatchHistoryIngestor ingestor;
    private final InMemoryTeamMatchHistoryStorage storage;

    public TeamMatchIngestorTest(final MockServerClient mockServer) {
        this.mockServer = mockServer;

        storage = new InMemoryTeamMatchHistoryStorage();
        var apiClient = new SoccerApiClient(
            "http://localhost:8090",
            new RestTemplate(),
            new ObjectMapper().registerModule(new JavaTimeModule())
        );

        ingestor = new TeamMatchHistoryIngestor(
            apiClient,
            storage
        );
    }

    @BeforeEach
    void reset() {
        storage.reset();
    }

    @Test
    void shouldIngestJsonFromRestApi() {
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

        var actual = storage.getState();
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
