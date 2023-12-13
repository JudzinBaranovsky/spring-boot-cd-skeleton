package org.bananalaba.springcdtemplate.task;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.Validate.notBlank;

import lombok.NonNull;

public class TimeHoleTask {

    private final String name;

    private final long durationMillis;
    private final Counter counter;
    private final Timer timer;

    public TimeHoleTask(String name, long durationMillis, @NonNull Counter counter, @NonNull Timer timer) {
        this.name = notBlank(name, "name required");

        checkArgument(durationMillis >= 0, "duration must be >= 0");
        this.durationMillis = durationMillis;

        this.counter = counter;
        this.timer = timer;
    }

    public void execute() {
        if (durationMillis > 0) {
            try {
                timer.countDown(durationMillis);
            } catch (InterruptedException e) {
                throw new ScheduleException("failed to count down", e);
            }
        }

        counter.increment(name);
    }

}
