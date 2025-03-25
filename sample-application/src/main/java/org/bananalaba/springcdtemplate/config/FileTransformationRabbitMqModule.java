package org.bananalaba.springcdtemplate.config;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setContainerFactory(fileTransformationRequestAmqpListenerContainerFactory());
    }

    @Bean
    public RabbitListenerContainerFactory<SimpleMessageListenerContainer> fileTransformationRequestAmqpListenerContainerFactory() {
        var factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(fileTransformatioRabbitConnectionFactory());
        factory.setMessageConverter(fileTransformationAmqpMessageConverter());

        return factory;
    }

    @Bean
    public AmqpTemplate amqpTemplate() {
        var template = new RabbitTemplate(fileTransformatioRabbitConnectionFactory());
        template.setUseTemporaryReplyQueues(true);
        template.setMessageConverter(fileTransformationAmqpMessageConverter());

        return template;
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

}
