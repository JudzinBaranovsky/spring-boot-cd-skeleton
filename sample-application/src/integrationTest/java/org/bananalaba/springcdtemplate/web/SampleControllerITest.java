package org.bananalaba.springcdtemplate.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import no.nav.security.mock.oauth2.MockOAuth2Server;
import no.nav.security.mock.oauth2.token.DefaultOAuth2TokenCallback;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "auth0.resource.issuer = http://localhost:8070/default",
    "auth0.resource.audience = test_audience"
})
public class SampleControllerITest {

    static {
        System.setProperty("node.ip", "192.168.0.1");
    }

    @LocalServerPort
    private int port;

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

        var client = new TestRestClient("http://localhost:" + port, "http://localhost:8070/default/token", "test-client", "test-secret");
        var actual = client.getStatus();

        assertThat(actual.getMessage()).isEqualTo("status: up");

        var tokenRequest = authServer.takeRequest();
        assertThat(tokenRequest.getPath()).isEqualTo("/default/token");

        var tokenRequestAuth = tokenRequest.getHeader("Authorization");
        var expectedAuth = getAuthorisationHeader("test-client", "test-secret");
        assertThat(tokenRequestAuth).isEqualTo(expectedAuth);
    }

    private String getAuthorisationHeader(final String userName, final String password) {
        String credentials = userName + ":" + password;
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        return "Basic " + encoded;
    }

}
