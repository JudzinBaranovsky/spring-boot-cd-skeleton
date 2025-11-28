package org.bananalaba.springcdtemplate.web;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.JsonParseException;
import com.nimbusds.oauth2.sdk.ClientCredentialsGrant;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.id.ClientID;
import org.bananalaba.springcdtemplate.model.SampleDto;

public class TestRestClient {

    private String baseUrl;
    private String authUrl;

    private String clientId;
    private String clientSecret;

    private HttpClient httpClient;
    private ObjectMapper jsonMapper;

    public TestRestClient(final String baseUrl, final String authUrl, final String clientId, final String clientSecret) {
        this.baseUrl = baseUrl;
        this.authUrl = authUrl;

        this.clientId = clientId;
        this.clientSecret = clientSecret;

        httpClient = HttpClient.newBuilder().build();
        jsonMapper = new ObjectMapper();
    }

    public SampleDto getStatus() throws Exception {
        var token = fetchAccessToken();
        var request = HttpRequest.newBuilder()
            .uri(new URI(baseUrl + "/api/v1/status"))
            .header("Authorization", "Bearer " + token)
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("non-200 response status: " + response.statusCode());
        }

        var rawResponseBody = response.body();
        try {
            return jsonMapper.readValue(rawResponseBody, SampleDto.class);
        } catch (JsonParseException e) {
            throw new RuntimeException("failed to parse response", e);
        }
    }

    private String fetchAccessToken() throws Exception {
        var tokenEndpoint = new URI(authUrl);
        var clientAuth = new ClientSecretBasic(new ClientID(clientId), new Secret(clientSecret));
        var clientGrant = new ClientCredentialsGrant();
        var scope = new Scope("status:read");
        var request = new TokenRequest(tokenEndpoint, clientAuth, clientGrant, scope);

        var response = TokenResponse.parse(request.toHTTPRequest().send());
        if (!response.indicatesSuccess()) {
            throw new RuntimeException("Token request failed: " + response.toErrorResponse().getErrorObject().getDescription());
        }

        var accessToken = response.toSuccessResponse().getTokens().getAccessToken();
        return accessToken.getValue();
    }

}
