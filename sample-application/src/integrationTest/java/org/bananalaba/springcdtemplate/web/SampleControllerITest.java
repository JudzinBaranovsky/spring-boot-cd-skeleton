package org.bananalaba.springcdtemplate.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.bananalaba.springcdtemplate.model.SampleDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import(TestClientConfig.class)
@ActiveProfiles("test")
public class SampleControllerITest {

    static {
        System.setProperty("node.ip", "192.168.0.1");
    }

    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void shouldNotAllowRequestsOverInsecureChannel() {
        String httpUrl = "http://localhost:" + port + "/api/v1/sample/hello";

        assertThatThrownBy(() -> restTemplate.getForObject(httpUrl, SampleDto.class))
            .isInstanceOf(HttpClientErrorException.class)
            .hasMessageContaining("TLS");
    }

    @Test
    public void shouldAllowHttpsRequests() {
        String httpUrl = "https://localhost:" + port + "/api/v1/sample/hello";

        var actual = restTemplate.getForObject(httpUrl, SampleDto.class);

        var expected = new SampleDto("Hello, World", "192.168.0.1");
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

}
