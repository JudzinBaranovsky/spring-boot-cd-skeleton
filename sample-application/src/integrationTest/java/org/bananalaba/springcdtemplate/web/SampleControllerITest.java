package org.bananalaba.springcdtemplate.web;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@WireMockTest(httpPort = 8081)
public class SampleControllerITest {

    static {
        System.setProperty("node.ip", "192.168.0.1");
        System.setProperty("client.numberService.host", "http://localhost:8081");
    }

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldGreet(WireMockRuntimeInfo wireMockRuntime) throws Exception {
        wireMockRuntime.getWireMock().register(WireMock.get("/api/v1/numbers/random").willReturn(ok("50")));

        var actual = mvc.perform(MockMvcRequestBuilders.get("/api/v1/sample/hello")).andReturn().getResponse();

        assertThat(actual.getStatus()).isEqualTo(200);
        assertThat(actual.getContentType()).isEqualTo("application/json");
        assertThat(actual.getContentAsString()).isEqualTo("{\"message\":\"Hello, World\",\"nodeIp\":\"192.168.0.1\",\"luckyNumber\":50}");
    }

}
