package org.bananalaba.springcdtemplate.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.bananalaba.springcdtemplate.model.QuoteDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Import(TestConfig.class)
@AutoConfigureMockMvc
@Testcontainers
public class SampleControllerITest {

    static {
        System.setProperty("integration.zen-quotes.api.url", "https://zenquotes.io/");
    }

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper jsonMapper;

    @Test
    public void shouldGreet() throws Exception {
        var actual = mvc.perform(get("/api/v1/quotes/random")).andReturn().getResponse();

        assertThat(actual.getStatus()).isEqualTo(200);
        assertThat(actual.getContentType()).isEqualTo("application/json");

        var responseDto = toQuoteDto(actual.getContentAsString());
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getMessage()).isNotBlank();
    }

    @SneakyThrows
    private QuoteDto toQuoteDto(final String json) {
        return jsonMapper.readValue(json, QuoteDto.class);
    }

}
