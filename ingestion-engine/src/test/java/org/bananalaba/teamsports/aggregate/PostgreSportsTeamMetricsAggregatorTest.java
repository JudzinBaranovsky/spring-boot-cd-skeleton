package org.bananalaba.teamsports.aggregate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import org.bananalaba.teamsports.AbstractTest;
import org.bananalaba.teamsports.TestDbToolkit;
import org.bananalaba.teamsports.aggregate.SportsTeamHistoryAggregate.AggregateMetric;
import org.bananalaba.teamsports.ingest.TeamMatchHistoryIngestor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;

public class PostgreSportsTeamMetricsAggregatorTest extends AbstractTest {

    private final TeamMatchHistoryIngestor ingestor;
    private final SportsTeamMetricsAggregator aggregator;
    private final TestDbToolkit testDbToolkit;

    @Autowired
    public PostgreSportsTeamMetricsAggregatorTest(TeamMatchHistoryIngestor ingestor, SportsTeamMetricsAggregator aggregator, TestDbToolkit testDbToolkit) {
        this.ingestor = ingestor;
        this.aggregator = aggregator;
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

        aggregator.precomputeMetrics();
        aggregator.precomputeMetrics(); // to test if upserts work

        var actualAggregate = aggregator.aggregateAll();
        var expectedAggregate = new SportsTeamHistoryAggregate(
            new AggregateMetric("Basque Country", 2),
            new AggregateMetric("Basque Country", 5),
            new AggregateMetric("TeamB", 0)
        );
        assertThat(actualAggregate).usingRecursiveComparison().isEqualTo(expectedAggregate);
    }

}
