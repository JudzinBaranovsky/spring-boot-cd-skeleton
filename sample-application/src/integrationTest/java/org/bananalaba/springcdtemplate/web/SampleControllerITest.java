package org.bananalaba.springcdtemplate.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;
import java.util.Map;

import no.nav.security.mock.oauth2.MockOAuth2Server;
import no.nav.security.mock.oauth2.token.DefaultOAuth2TokenCallback;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "auth0.resource.issuer = http://localhost:8070/default",
    "auth0.resource.audience = test_audience"
})
public class SampleControllerITest {

    static {
        System.setProperty("node.ip", "192.168.0.1");
    }

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldGreet() throws Exception {
        var authServer = new MockOAuth2Server();
        authServer.start(8070);

        var tokenCallback = new DefaultOAuth2TokenCallback(
            "default",
            "foo",
            "JWT",
            List.of("test_audience"),
            Map.of(
                "scope", "read:status"
            ),
            3600
        );
        authServer.enqueueCallback(tokenCallback);

        var token = authServer.issueToken("default", "test_client", tokenCallback).serialize();

        var actual = mvc.perform(get("/api/v1/status").header("Authorization", "Bearer " + token)).andReturn().getResponse();

        assertThat(actual.getStatus()).isEqualTo(200);
        assertThat(actual.getContentType()).isEqualTo("application/json");
        assertThat(actual.getContentAsString()).isEqualTo("{\"message\":\"status: up\",\"nodeIp\":\"192.168.0.1\"}");
    }

}
