package org.bananalaba.springcdtemplate.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import org.bananalaba.springcdtemplate.data.model.MessageRecord;
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
public class MessageControllerTest {

    static {
        System.setProperty("integration.zen-quotes.api.url", "https://zenquotes.io/");
    }

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper jsonMapper;

    @Test
    public void shouldCreateMessage() throws Exception {
        var message = new MessageRecord(null, "test message");

        var postResponse = mvc.perform(post("/api/v1/messages").content(toJson(message)))
            .andReturn()
            .getResponse();
        assertThat(postResponse.getStatus()).isEqualTo(201);

        var getResponse = mvc.perform(get("/api/v1/messages"))
            .andReturn()
            .getResponse();
        assertThat(getResponse.getStatus()).isEqualTo(200);

        var actualJson = getResponse.getContentAsString();
        var actualMessageText = JsonPath.parse(actualJson).read("$._embedded.messages[0].text", String.class);
        assertThat(actualMessageText).isEqualTo(message.getText());
    }

    @SneakyThrows
    private String toJson(final MessageRecord record) {
        return jsonMapper.writeValueAsString(record);
    }


}
