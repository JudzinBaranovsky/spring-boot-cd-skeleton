package org.bananalaba.springcdtemplate.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bananalaba.teamsports.aggregate.SportsTeamHistoryAggregate;
import org.bananalaba.teamsports.aggregate.SportsTeamHistoryAggregate.AggregateMetric;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "SOCCER_API_BASE_URL=http://localhost:8090"
})
public class SampleControllerITest {

    @Autowired
    private MockMvc mvc;
    private ObjectMapper jsonMapper = new ObjectMapper();

    @Test
    public void shouldReturnEmptyResultsByDefault() throws Exception {
        var actual = mvc.perform(get("/api/v1/sports/aggregate")).andReturn().getResponse();

        assertThat(actual.getStatus()).isEqualTo(200);
        assertThat(actual.getContentType()).isEqualTo("application/json");

        var responseJson = actual.getContentAsString();
        var responseBody = parse(responseJson);

        var expected = new SportsTeamHistoryAggregate(
            new AggregateMetric("<no-data>", 0),
            new AggregateMetric("<no-data>", 0),
            new AggregateMetric("<no-data>", 0)
        );
        assertThat(responseBody).usingRecursiveComparison().isEqualTo(expected);
    }

    private SportsTeamHistoryAggregate parse(String json) throws IOException {
        return jsonMapper.readValue(json, SportsTeamHistoryAggregate.class);
    }

}
