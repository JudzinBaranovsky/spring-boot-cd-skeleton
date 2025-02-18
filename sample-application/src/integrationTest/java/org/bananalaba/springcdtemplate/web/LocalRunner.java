package org.bananalaba.springcdtemplate.web;

import org.bananalaba.springcdtemplate.SampleApplication;
import org.springframework.boot.SpringApplication;

public class LocalRunner {

    public static void main(final String[] arguments) {
        System.setProperty("node.ip", "127.0.0.1");
        System.setProperty("management.tracing.enabled", "false");
        SpringApplication.run(SampleApplication.class, arguments);
    }

}
