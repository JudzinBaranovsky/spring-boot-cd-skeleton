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
### Option A - Docker Compose - **TO BE FIXED, NOT UP TO DATE** 
0. One-time setup
    1. Create a `local-infrastructure/env/docker/.env` file with the following properties:
   ```
    KIBANA_SYSTEM_PASSWORD=<some password>
    ELASTIC_PASSWORD=<some password>
    LOGSTASH_INTERNAL_PASSWORD=<some password>
    FILEBEAT_INTERNAL_PASSWORD=<some password>
    BEATS_SYSTEM_PASSWORD=<some password>
    ELASTIC_VERSION=8.7.1
   ```
    2. Run `local-infrastructure/manage-env.ps1 setup` - this will deploy ElasticSearch 8 + Kibana 8 + Jaeger all-in-one for monitoring.
1. From the `local-infrastructure` folder
    1. Run `manage-env.ps1 start`.
    2. Run `manage-app.ps1 start`.
    3. Use `http://localhost:8080` to interact with the application.
    4. Use `http://localhost:5601` to search logs in Kibana (authorise as `elastic` with ELASTIC_PASSWORD).
    5. Use 'http:localhost:16686' to search traces in Jaeger.
2. Other commands
    1. `manage-env.ps1 recreate` - delete the application and monitoring containers, and create the monitoring containers from scratch
    2. `manage-env.ps1 destroy` - delete the application and monitoring containers
    3. `manage-env.ps1 stop` - stop the application and monitoring containers
    4. `manage-app.ps1 stop` - stop the application container
    5. `manage-app.ps1 destroy` - stop and remove the application container

### Option B - from IDE
Simply run the `LocalRunner` main class from `sample-application/src/integrationTest`.

### Exploring the APIs
Visit `http://localhost:8080/swagger-ui/index.html` for interactive API docs.

## Local E2E
1. Run locally (see above).
2. Run the tests: `gradlew :e2e-tests:acceptanceTest`
