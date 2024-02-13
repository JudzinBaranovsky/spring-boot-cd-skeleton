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
1. JDK 20+
2. Docker daemon and Docker Compose CLI.

## Modules
1. sample-model - the models shared across the applications
2. sample-application - a REST API for managing messages
   1. CRUD API for messages protected with OAuth2 JWT authorisation
   2. CORS controlled via the `web.cors.allowedOrigins` property
   3. by default any authorised user with read permissions in JWT may read any message and create new messages
   4. by default only the user created a message may delete/update that message
3. message-store-ui - a very simplistic Spring Boot/Thymeleaf UI on top of the messages REST API
   1. uses the authorisation grant OAuth2 flow via Auth0 to authenticate and authorise users
   2. obtains JWT tokens with claims enabling access to user's profile, retrieval of messages, persistence of messages, and removal of messages
   3. must be added to CORS allowed origins in the REST API

## Building
1. Build, test, and analyse the code: `gradlew build`.
2. Build an application image: `gradlew bootBuildImage`.

## Running locally
0. **One-time setup**
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
    3. Create a developer account in Auth0.
       1. Consider using a VPN attached to a USA location to avoid registration issues.
1. From the `local-infrastructure` folder
    1. Run `manage-env.ps1 start`.
    2. Run `manage-app.ps1 start`.
    3. Use `http://localhost:8080` to interact with the REST application.
    4. Use `http://localhost:8090/web/home` to interact with the UI application.
       1. In case of issues with authorisation via Auth0, try a VPN connected to a USA location.
    5. Use `http://localhost:5601` to search logs in Kibana (authorise as `elastic` with ELASTIC_PASSWORD).
    6. Use 'http:localhost:16686' to search traces in Jaeger.
2. Other commands
    1. `manage-env.ps1 recreate` - delete the application and monitoring containers, and create the monitoring containers from scratch
    2. `manage-env.ps1 destroy` - delete the application and monitoring containers
    3. `manage-env.ps1 stop` - stop the application and monitoring containers
    4. `manage-app.ps1 stop` - stop the application container
    5. `manage-app.ps1 destroy` - stop and remove the application container

## Local E2E
1. Run locally (see above).
2. Run the tests: `gradlew :e2e-tests:acceptanceTest`
