package org.bananalaba.springcdtemplate.proxy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.endpoint.DefaultClientCredentialsTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2ClientCredentialsGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2ClientCredentialsGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Configuration
public class ProxySecurityConfig {

    @Value("${auth0.resource.audience}")
    private String statusApiAudience;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .oauth2Login(AbstractHttpConfigurer::disable)
            .oauth2Client(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public RestClient restClient(OAuth2AuthorizedClientManager authorizedClientManager) {
        OAuth2ClientHttpRequestInterceptor requestInterceptor =
            new OAuth2ClientHttpRequestInterceptor(authorizedClientManager);
        return RestClient.builder()
            .requestInterceptor(requestInterceptor)
            .build();
    }

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2ClientCredentialsGrantRequest> clientCredentialsAccessTokenResponseClient() {
        OAuth2ClientCredentialsGrantRequestEntityConverter requestEntityConverter =
            new OAuth2ClientCredentialsGrantRequestEntityConverter();
        requestEntityConverter.addParametersConverter(parametersConverter());

        DefaultClientCredentialsTokenResponseClient accessTokenResponseClient =
            new DefaultClientCredentialsTokenResponseClient();
        accessTokenResponseClient.setRequestEntityConverter(requestEntityConverter);

        return accessTokenResponseClient;
    }

    private Converter<OAuth2ClientCredentialsGrantRequest, MultiValueMap<String, String>> parametersConverter() {
        return (grantRequest) -> {
            MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
            parameters.set("audience", statusApiAudience);

            return parameters;
        };
    }

}
