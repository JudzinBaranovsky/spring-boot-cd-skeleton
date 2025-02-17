package org.bananalaba.springcdtemplate.test;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.CreateSecretRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;
import org.testcontainers.utility.DockerImageName;

@Slf4j
public class TestLocalStackManager implements TestExecutionListener, Ordered {

    private static final DockerImageName LOCAL_STACK_IMAGE = DockerImageName.parse("localstack/localstack:0.11.3");

    private LocalStackContainer localStackContainer;

    @Override
    public void beforeTestClass(TestContext testContext) {
        log.info("starting the LocalStack container");
        localStackContainer = new LocalStackContainer(LOCAL_STACK_IMAGE).withServices(Service.SECRETSMANAGER);
        localStackContainer.start();
        log.info("started the LocalStack container");

        var accessKey = localStackContainer.getAccessKey();
        System.setProperty("aws.accessKeyId", accessKey);
        log.info("system properties: injected access key: " + accessKey);

        var secretKey = localStackContainer.getSecretKey();
        System.setProperty("aws.secretAccessKey", secretKey);
        log.info("system properties: injected secret key: " + secretKey);

        var secretsManager = AWSSecretsManagerClientBuilder.standard()
            .withEndpointConfiguration(new EndpointConfiguration(
                localStackContainer.getEndpoint().toString(),
                localStackContainer.getRegion()
            ))
            .withCredentials(new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(
                    accessKey,
                    secretKey
                )
            ))
            .build();

        var secretsCreationRequest = new CreateSecretRequest()
            .withName("test-secret")
            .withSecretString("{}");
        var creationResult = secretsManager.createSecret(secretsCreationRequest);
        log.info("created test secrets at ARN: " + creationResult.getARN());
    }

    @Override
    public void afterTestClass(TestContext testContext) {
        log.info("removing the LocalStack container");
        localStackContainer.stop();
        log.info("removed the LocalStack container");
    }

    @Override
    public int getOrder() {
        return -1000;
    }

}
