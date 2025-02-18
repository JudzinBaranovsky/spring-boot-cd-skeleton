# Skeleton instantiation steps

1. Folders
    1. Rename `sample-model` and `sample-application` folders.
2. Gradle
    1. Update the group in the root build file.
    2. Update the model dependency in the application build file.
3. Code
    1. Rename the root packages in model and application folders.
    2. Rename the SampleController and SampleApplication classes and their tests from the application module.
    3. Rename the SampleDto from the model module.
    4. Change the `spring.application.name` in the `application.properties`.
    5. Change the `FileNamePattern` in inside the `RollingFile` appender in the `logback-spring.xml`.
6. Docker
    1. Change the `imageName` in the `buildBootImage` task in the application build script.
    2. Change the service name in the `local-infrastructure/app/docker/docker-compose.yaml`.
7. GitHub Workflow
    1. `build.yml`
        1. Change the `PACKAGE_NAME` parameter everywhere in the script.
        2. Change the `docker tag` command parameters in the `Docker Build-Tag-Push` step.
    2. Change the Maven repository name in the application build script in the publishing section.

# How to build and run

## Prerequisites
1. Docker daemon and Docker Compose CLI.
2. K8s (minikube or K8s built into Docker Desktop).
3. Kubectl.
4. Helm.

## Building
1. Build, test, and analyse the code: `gradlew build`.
2. Build an application image: `gradlew bootBuildImage`.

## Running locally
### Docker Compose
This method is not updated for this branch. Need to align the env variables and add missing containers if need to use this way.

### IDE
Run the LocalRunner main class from `sample-application/src/integrationTest`.

## Local E2E
1. Run locally (see above).
2. Run the tests: `gradlew :e2e-tests:acceptanceTest`
