package org.bananalaba.springcdtemplate.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.bananalaba.springcdtemplate.client.TipsClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClientException;

@SpringBootTest
@AutoConfigureMockMvc
public class SampleControllerITest {

    static {
        System.setProperty("node.ip", "192.168.0.1");
    }

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TipsClient tipsClient;

    @Test
    public void shouldGreet() throws Exception {
        when(tipsClient.getTipOfTheDay()).thenReturn("great tip");

        var actual = mvc.perform(get("/api/v1/sample/hello")).andReturn().getResponse();

        assertThat(actual.getStatus()).isEqualTo(200);
        assertThat(actual.getContentType()).isEqualTo("application/json");
        assertThat(actual.getContentAsString()).isEqualTo("{\"message\":\"Hello, World! Your tip: great tip\",\"nodeIp\":\"192.168.0.1\"}");
    }

    @Test
    public void shouldFallBackToGreetingWithDefaultTip() throws Exception {
        when(tipsClient.getTipOfTheDay()).thenThrow(new RestClientException("failed to connect"));

        var actual = mvc.perform(get("/api/v1/sample/hello")).andReturn().getResponse();

        assertThat(actual.getStatus()).isEqualTo(200);
        assertThat(actual.getContentType()).isEqualTo("application/json");
        assertThat(actual.getContentAsString()).isEqualTo("{\"message\":\"Hello, World! Your tip: Think positively!\",\"nodeIp\":\"192.168.0.1\"}");
    }

}
