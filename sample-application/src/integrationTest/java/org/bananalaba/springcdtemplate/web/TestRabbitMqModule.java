package org.bananalaba.springcdtemplate.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
public class TestRabbitMqModule {

    @Bean
    public static RabbitMQContainer rabbitMQContainer() {
        return new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.7.25-management-alpine"));
    }

    @Bean
    public static RabbitConnectionDetails rabbitConnectionDetails(final RabbitMQContainer container,
                                                                  @Value("${fileTransformation.amqp.queue.name}") final String testQueueName) throws Exception{
        container.execInContainer("rabbitmqadmin", "declare", "queue", "name=" + testQueueName);

        return new RabbitConnectionDetails() {

            @Override
            public List<Address> getAddresses() {
                return List.of(new Address(
                    container.getHost(),
                    container.getAmqpPort()
                ));
            }

            @Override
            public String getUsername() {
                return container.getAdminUsername();
            }

            @Override
            public String getPassword() {
                return container.getAdminPassword();
            }

        };
    }

}
