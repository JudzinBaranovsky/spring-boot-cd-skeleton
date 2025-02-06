package org.bananalaba.springcdtemplate.config;

import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.bananalaba.springcdtemplate.client.ZenQuotesApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(FeignClientsConfiguration.class)
public class ZenQuotesApiConfig {

    @Bean
    public ZenQuotesApiClient zenQuotesApiClient(final @Value("${integration.zen-quotes.api.url}") String baseUrl,
                                                 final Contract contract,
                                                 final Encoder encoder,
                                                 final Decoder decoder) {
        return Feign.builder()
            .encoder(encoder)
            .decoder(decoder)
            .contract(contract)
            .target(ZenQuotesApiClient.class, baseUrl);
    }

}
