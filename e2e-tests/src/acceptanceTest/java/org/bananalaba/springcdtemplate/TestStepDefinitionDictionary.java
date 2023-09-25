package org.bananalaba.springcdtemplate;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class TestStepDefinitionDictionary {

    @Value("${application.baseUrl}")
    private String baseUrl;
    @Autowired
    private RestTemplate rest;

    private Integer lastStatusCode;
    private String lastResponseBody;

    @When("^the client calls (.+)$")
    public void the_client_calls(String url) {
        rest.execute(
            baseUrl + "/" + url,
            HttpMethod.GET,
            request -> { },
            response -> {
                lastStatusCode = response.getStatusCode().value();
                lastResponseBody = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
                return null;
            }
        );
    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int code) {
        assertThat(lastStatusCode)
            .describedAs("the client must receive " + code + " response code")
            .isEqualTo(code);
    }

    @Then("^the client receives json body (.+)$")
    public void the_client_receives_json_body(String expected) throws JSONException {
        JSONAssert.assertEquals(expected, lastResponseBody, false);
    }

}
