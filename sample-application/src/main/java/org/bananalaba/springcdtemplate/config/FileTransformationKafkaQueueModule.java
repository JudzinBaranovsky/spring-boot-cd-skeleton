package org.bananalaba.springcdtemplate.config;

import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.bananalaba.springcdtemplate.dto.FileTransformationRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class FileTransformationKafkaQueueModule {

    private final KafkaConnectionDetails kafkaConnectionDetails;
    @Value("${fileTransformation.kafka.topic.pollMs:1000}")
    private final int maxPollMs;

    @NonNull
    @Qualifier("fileTransformationJsonMapper")
    private final ObjectMapper jsonMapper;

    @Bean
    public KafkaTemplate<String, FileTransformationRequest> fileTransformationRequestKafkaTemplate() {
        var template = new KafkaTemplate<>(fileTransformationRequestProducerFactory());
        template.setObservationEnabled(true);

        return template;
    }

    @Bean
    public ProducerFactory<String, FileTransformationRequest> fileTransformationRequestProducerFactory() {
        var props = new HashMap<String, Object>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConnectionDetails.getBootstrapServers());

        var valueSerializer = new JsonSerializer<>(jsonMapper)
            .copyWithType(FileTransformationRequest.class);
        return new DefaultKafkaProducerFactory<>(props, new StringSerializer(), valueSerializer);
    }

    @Bean
    public KafkaListenerContainerFactory<?> fileTransformationRequestKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, FileTransformationRequest>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setObservationEnabled(true);

        return factory;
    }

    @Bean
    public ConsumerFactory<String, FileTransformationRequest> consumerFactory() {
        var props = new HashMap<String, Object>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConnectionDetails.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "file-transformation-request-registrar");
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollMs);

        var valueDeserializer = new JsonDeserializer<>(jsonMapper)
            .copyWithType(FileTransformationRequest.class);
        valueDeserializer.addTrustedPackages(FileTransformationRequest.class.getPackageName());
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), valueDeserializer);
    }

}
