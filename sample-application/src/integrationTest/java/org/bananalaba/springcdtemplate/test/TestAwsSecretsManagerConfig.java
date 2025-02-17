package org.bananalaba.springcdtemplate.test;

import java.net.URI;

import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.BootstrapRegistryInitializer;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

public class TestAwsSecretsManagerConfig implements BootstrapRegistryInitializer {

    @Override
    public void initialize(BootstrapRegistry registry) {
        var accessKey = System.getProperty("aws.accessKeyId");
        var secretKey = System.getProperty("aws.secretAccessKey");
        var region = System.getProperty("aws.region");
        var endpoint = System.getProperty("aws.endpoint");

        registry.register(
            SecretsManagerClient.class, context -> {
                AwsCredentialsProvider awsCredentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey));
                return SecretsManagerClient.builder()
                    .credentialsProvider(awsCredentialsProvider)
                    .region(Region.of(region))
                    .endpointOverride(URI.create(endpoint))
                    .build();
            });
    }

}
