package org.bananalaba.springcdtemplate.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.bananalaba.springcdtemplate.dto.FileTransformationStatusDto.StatusCode.COMPLETED;
import static org.bananalaba.springcdtemplate.dto.FileTransformationStatusDto.StatusCode.IN_PROGRESS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bananalaba.springcdtemplate.dto.FileTransformationDefinitionDto;
import org.bananalaba.springcdtemplate.dto.FileTransformationStatusDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Import({
    TestKafkaModule.class,
    TestRabbitMqModule.class
})
@TestPropertySource(properties = {
    "fileTransformation.kafka.topic.name=test-transformations",
    "fileTransformation.amqp.queue.name=test-transformations",
    "fileTransformation.amqp.routing.key=test-transformations"
})
public class FileTransformationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper jsonMapper;

    @Test
    public void shouldProcessTask() throws Exception {
        var taskDefinition = FileTransformationDefinitionDto.builder()
            .inputFileUrl("urlA")
            .outputFilePath("urlB")
            .parameters(Map.of("paramA", "valueA"))
            .build();
        var submissionContent = toJson(taskDefinition);
        var submissionResponse = mvc.perform(post("/api/v1/file-transformations/submitAsync").content(submissionContent).contentType("application/json"))
            .andReturn()
            .getResponse();

        assertThat(submissionResponse.getStatus()).isEqualTo(200);
        assertThat(submissionResponse.getContentType()).isEqualTo("application/json");

        var initialStatus = fromJson(submissionResponse.getContentAsString(), FileTransformationStatusDto.class);
        assertThat(initialStatus.getTaskId()).isNotEmpty();
        assertThat(initialStatus.getStatus()).isEqualTo(IN_PROGRESS);

        Thread.sleep(6000);

        var statusResponse = mvc.perform(get("/api/v1/file-transformations/status/" + initialStatus.getTaskId()))
            .andReturn()
            .getResponse();

        assertThat(statusResponse.getStatus()).isEqualTo(200);
        assertThat(statusResponse.getContentType()).isEqualTo("application/json");

        var statusAfterUpdate = fromJson(statusResponse.getContentAsString(), FileTransformationStatusDto.class);
        assertThat(statusAfterUpdate.getTaskId()).isEqualTo(initialStatus.getTaskId());
        assertThat(statusAfterUpdate.getStatus()).isEqualTo(COMPLETED);
    }

    private String toJson(final Object object) {
        try {
            return jsonMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T fromJson(final String json, final Class<T> type) {
        try {
            return jsonMapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
