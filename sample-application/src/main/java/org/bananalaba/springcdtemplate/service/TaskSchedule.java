package org.bananalaba.springcdtemplate.service;

import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.task.TimeHoleTask;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskSchedule {

    @Qualifier("sequentialTask")
    private final TimeHoleTask sequentialTask;

    @Qualifier("concurrentTask")
    private final TimeHoleTask concurrentTask;

    @Scheduled(fixedRate = 100)
    public void runAtFixedRate() {
        sequentialTask.execute();
    }

    @Async("dedicatedTaskExecutor")
    @Scheduled(fixedRate = 100)
    public void runConcurrently() {
        concurrentTask.execute();
    }

}
