package org.bananalaba.springcdtemplate.local;

import org.bananalaba.springcdtemplate.SampleApplication;
import org.springframework.boot.SpringApplication;

public class LocalRunner {

    public static void main(String[] arguments) {
        System.setProperty("management.tracing.enabled", "false");
        SpringApplication.from(SampleApplication::main)
            .with(LocalRunnerConfig.class)
            .run(arguments);
    }

}
