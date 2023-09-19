# Skeleton instantiation steps

1. Folders
   1Rename `sample-model` and `sample-application` folders.
2. Gradle
   1. Update the group in the root build file.
   2. Update the model dependency in the application build file.
3. Code
   1. Rename the root packages in model and application folders.
   2. Rename the SampleController and SampleApplication classes and their tests from the application module.
   3. Rename the SampleDto from the model module.

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
0. **One-time setup**
    1. Create a `secrets.properties` (it will be Git-ignored) file in the `local-infrastructure` folder.
    2. Define an `elasticsearch_password` property in `secrets.properties`.
1. From the `local-infrastructure` folder
   1. Run `deploy-env.ps1` - this will deploy ElasticSearch 7 + Kibana 7 + FluentD for monitoring.
   2. Run `deploy-app.ps1`.
2. To dispose the local deployment, from the `local-infrastructure` folder
   1. Run `dispose-app.ps1` to bring down the application itself.
   2. Run `dispose-env.ps1 -mode <mode>` where `mode` may be `all` or `fluentd`.
