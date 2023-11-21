package org.bananalaba.springcdtemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SampleApplicationConfig {

    @Bean
    public RestTemplate numberServiceTemplate(RestTemplateBuilder builder) {
        var numberConverter = new HttpMessageConverter<Long>() {

            @Override
            public boolean canRead(Class<?> clazz, MediaType mediaType) {
                return clazz == Long.class;
            }

            @Override
            public boolean canWrite(Class<?> clazz, MediaType mediaType) {
                return false;
            }

            @Override
            public List<MediaType> getSupportedMediaTypes() {
                return List.of(MediaType.TEXT_PLAIN);
            }

            @Override
            public Long read(Class<? extends Long> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
                var bytes = inputMessage.getBody().readAllBytes();
                var string = new String(bytes, StandardCharsets.UTF_8);

                return Long.parseLong(string);
            }

            @Override
            public void write(Long aLong, MediaType contentType, HttpOutputMessage outputMessage) {
                throw new UnsupportedOperationException();
            }

        };

        return builder.additionalMessageConverters(numberConverter)
            .build();
    }

}
