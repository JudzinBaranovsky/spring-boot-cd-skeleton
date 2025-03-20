package org.bananalaba.springcdtemplate.config;

import java.util.UUID;

import org.bananalaba.springcdtemplate.queue.FileTransformationQueue;
import org.bananalaba.springcdtemplate.queue.InMemoryFileTransformationQueue;
import org.bananalaba.springcdtemplate.service.SystemClock;
import org.bananalaba.springcdtemplate.service.TaskIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class FileTransformationModule {

    @Bean
    public SystemClock systemClock() {
        return new SystemClock();
    }

    @Bean
    public TaskIdGenerator taskIdGenerator() {
        return () -> UUID.randomUUID().toString();
    }

    @Bean
    public FileTransformationQueue fileTransformationQueue() {
        return new InMemoryFileTransformationQueue();
    }

}
