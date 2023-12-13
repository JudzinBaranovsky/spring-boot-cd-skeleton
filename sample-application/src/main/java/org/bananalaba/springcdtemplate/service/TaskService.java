package org.bananalaba.springcdtemplate.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledFuture;

import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.task.Counter;
import org.bananalaba.springcdtemplate.task.ScheduleException;
import org.bananalaba.springcdtemplate.task.TimeHoleTask;
import org.bananalaba.springcdtemplate.task.Timer;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskService {

    private final Timer timer;
    private final TaskScheduler scheduler;
    private final BlockingQueue<ScheduledFuture<?>> tasks = new LinkedBlockingQueue<>();

    public void schedule(String name, Counter counter, int rateMs) {
        var logic = new TimeHoleTask(name, 100, counter, timer);
        var task = scheduler.schedule(
            logic::execute,
            context -> Optional.ofNullable(context.lastActualExecution())
                .map(instant -> instant.plusMillis(rateMs))
                .orElse(Instant.now())
        );
        tasks.add(task);
    }

    public void cancelTasks() {
        var snapshot = new ArrayList<ScheduledFuture<?>>();
        tasks.drainTo(snapshot);

        snapshot.forEach(task -> {
            task.cancel(true);
            try {
                task.wait();
            } catch (InterruptedException e) {
                throw new ScheduleException("failed to stop task", e);
            }
        });
    }

}
