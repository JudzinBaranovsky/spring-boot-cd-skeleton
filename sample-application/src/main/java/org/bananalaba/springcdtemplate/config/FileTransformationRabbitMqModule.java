package org.bananalaba.springcdtemplate.config;

import java.util.List;
import java.util.stream.Collectors;

import brave.Tracing;
import brave.messaging.MessagingTracing;
import brave.spring.rabbit.SpringRabbitTracing;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
@RequiredArgsConstructor
public class FileTransformationRabbitMqModule implements RabbitListenerConfigurer {

    @NonNull
    private final RabbitConnectionDetails connectionDetails;

    @NonNull
    @Qualifier("fileTransformationJsonMapper")
    private final ObjectMapper jsonMapper;

    @Value("${fileTransformation.amqp.replyTimeoutMs:5000}")
    private final long syncTransformationTimeoutMs;

    @NonNull
    private final Tracing tracing;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setContainerFactory(fileTransformationRequestAmqpListenerContainerFactory());
    }

    @Bean
    public RabbitListenerContainerFactory<SimpleMessageListenerContainer> fileTransformationRequestAmqpListenerContainerFactory() {
        var factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(fileTransformatioRabbitConnectionFactory());
        factory.setMessageConverter(fileTransformationAmqpMessageConverter());
        factory.setObservationEnabled(true);

        return springRabbitTracing().decorateSimpleRabbitListenerContainerFactory(factory);
    }

    @Bean
    public AmqpTemplate amqpTemplate() {
        var template = new RabbitTemplate(fileTransformatioRabbitConnectionFactory());
        template.setUseTemporaryReplyQueues(true);
        template.setMessageConverter(fileTransformationAmqpMessageConverter());
        template.setReplyTimeout(syncTransformationTimeoutMs);
        template.setObservationEnabled(true);

        return springRabbitTracing().decorateRabbitTemplate(template);
    }

    @Bean
    public ConnectionFactory fileTransformatioRabbitConnectionFactory() {
        var factory = new CachingConnectionFactory();
        factory.setAddresses(toString(connectionDetails.getAddresses()));
        factory.setUsername(connectionDetails.getUsername());
        factory.setPassword(connectionDetails.getPassword());

        return factory;
    }

    private String toString(final List<Address> addresses) {
        return addresses.stream()
            .map(address -> address.host() + ":" + address.port())
            .collect(Collectors.joining(", "));
    }

    @Bean
    public MessageConverter fileTransformationAmqpMessageConverter() {
        return new Jackson2JsonMessageConverter(jsonMapper);
    }

    @Bean
    public SpringRabbitTracing springRabbitTracing() {
        return SpringRabbitTracing.newBuilder(messagingTracing())
            .remoteServiceName("my-mq-service")
            .build();
    }

    @Bean
    public MessagingTracing messagingTracing() {
        return MessagingTracing.create(tracing);
    }

}
