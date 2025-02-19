package org.bananalaba.springcdtemplate.proxy;

import org.springframework.boot.SpringApplication;

public class ProxyLocalRunner {

    public static void main(final String[] arguments) {
        System.setProperty("management.tracing.enabled", "false");
        SpringApplication.run(ProxyApplication.class, arguments);
    }

}
