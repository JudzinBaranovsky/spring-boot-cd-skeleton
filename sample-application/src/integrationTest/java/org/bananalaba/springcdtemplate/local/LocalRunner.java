package org.bananalaba.springcdtemplate.local;

import org.bananalaba.springcdtemplate.SampleApplication;
import org.springframework.boot.SpringApplication;

public class LocalRunner {

    private static void runWithProfile(final String profileName, final String[] arguments) {
        System.setProperty("management.tracing.enabled", "false");
        System.setProperty("spring.profiles.active", profileName);
        SpringApplication.from(SampleApplication::main)
            .with(LocalRunnerConfig.class)
            .run(arguments);
    }

    public static class PostgresqlRunner {

        public static void main(final String[] arguments) {
            runWithProfile("postgresql", arguments);
        }

    }

    public static class MongoDbRunner {

        public static void main(final String[] arguments) {
            runWithProfile("mongodb", arguments);
        }

    }

}
