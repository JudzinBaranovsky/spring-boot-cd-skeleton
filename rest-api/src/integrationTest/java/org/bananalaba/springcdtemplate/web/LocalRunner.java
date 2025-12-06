package org.bananalaba.springcdtemplate.web;

import org.bananalaba.springcdtemplate.DataAggregationApplication;
import org.springframework.boot.SpringApplication;

public class LocalRunner {

    private static void runWithProfile(final String profileName, final String[] arguments) {
        System.setProperty("management.tracing.enabled", "false");

        var effectiveProfile = profileName;
        if ("true".equalsIgnoreCase(System.getenv("ENABLE_AUTH"))) {
            effectiveProfile += ",s2s-auth";
        }

        System.setProperty("spring.profiles.active", effectiveProfile);
        SpringApplication.from(DataAggregationApplication::main)
            .with(LocalRunnerConfig.class)
            .run(arguments);
    }

    public static class DockerOnlyRunner {

        public static void main(final String[] arguments) {
            runWithProfile("docker", arguments);
        }

    }

    public static class NeonDbRunner {

        public static void main(final String[] arguments) {
            runWithProfile("neon-db", arguments);
        }

    }

}
