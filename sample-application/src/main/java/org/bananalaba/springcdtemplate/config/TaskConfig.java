package org.bananalaba.springcdtemplate.config;

import org.bananalaba.springcdtemplate.task.Counter;
import org.bananalaba.springcdtemplate.task.TimeHoleTask;
import org.bananalaba.springcdtemplate.task.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
@EnableAsync
public class TaskConfig {

    @Bean
    @Primary
    public TaskExecutor taskExecutor() {
        var executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(1);

        return executor;
    }

    @Bean
    public TaskExecutor dedicatedTaskExecutor() {
        var executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(2);
        executor.setThreadNamePrefix("dedicated-");

        return executor;
    }

    @Bean
    public TimeHoleTask sequentialTask(Counter counter, Timer timer) {
        return new TimeHoleTask("sequential", 100, counter, timer);
    }

    @Bean
    public TimeHoleTask concurrentTask(Counter counter, Timer timer) {
        return new TimeHoleTask("concurrent", 100, counter, timer);
    }

}
