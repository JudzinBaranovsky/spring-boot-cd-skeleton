package org.bananalaba.springcdtemplate.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bananalaba.springcdtemplate.client.DataServiceClient;
import org.bananalaba.springcdtemplate.data.BlobItemSummary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
    "image-repository.cacheSize=1",
    "image-repository.blobGenerationGrowthFactor=1",
    "image-repository.blobGenerationMinIterations=2",
    "image-repository.blobGenerationMaxIterations=2",
    "text-repository.cacheSize=1",
    "text-repository.blobGenerationGrowthFactor=1",
    "text-repository.blobGenerationMinIterations=2",
    "text-repository.blobGenerationMaxIterations=2",
    "video-repository.cacheSize=1",
    "video-repository.blobGenerationGrowthFactor=1",
    "video-repository.blobGenerationMinIterations=2",
    "video-repository.blobGenerationMaxIterations=2",
    "dataService.minLatency=0",
    "dataService.maxLatency=0",
    "sync.worker.minDelaysMs=0",
    "sync.worker.maxDelaysMs=0",
    "async.threads=1",
    "async.worker.minDelaysMs=0",
    "async.worker.maxDelaysMs=0"
})
@AutoConfigureMockMvc
@MockBean(classes = {DataServiceClient.class})
public class BlobControllerITest {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Test
    public void shouldGreet() throws Exception {
        var actual = mvc.perform(post("/api/v1/blob/process-image?id=1")).andReturn().getResponse();

        assertThat(actual.getStatus()).isEqualTo(200);
        assertThat(actual.getContentType()).isEqualTo("application/json");

        var rawContent = actual.getContentAsString();
        var content = jsonMapper.readValue(rawContent, BlobItemSummary.class);
        assertThat(content.getSizeInBytes()).isEqualTo(72);
        assertThat(content.getMetadata()).containsKeys("format", "compression");
    }

}
