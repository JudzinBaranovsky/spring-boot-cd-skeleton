package org.bananalaba.springcdtemplate.web;

import java.util.HashMap;
import java.util.List;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
public class TestKafkaModule {

    @Bean
    public static KafkaContainer kafkaTestContainer() {
        return new KafkaContainer(DockerImageName.parse("apache/kafka:3.8.0"));
    }

    @Bean
    public static KafkaConnectionDetails kafkaConnectionDetails(final KafkaContainer container,
                                                                @Value("${fileTransformation.kafka.topic.name}") final String topicName) {
        var configs = new HashMap<String, Object>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, container.getBootstrapServers());

        var admin = new KafkaAdmin(configs);
        admin.createOrModifyTopics(TopicBuilder.name(topicName)
            .partitions(3)
            .compact()
            .build()
        );

        return () -> List.of(container.getBootstrapServers());
    }

}
