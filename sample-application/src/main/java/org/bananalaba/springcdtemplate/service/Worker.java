package org.bananalaba.springcdtemplate.service;

import static com.google.common.base.Verify.verify;

import java.util.Random;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Worker {

    private final String name;

    private final long minDelay;
    private final long maxDelay;

    private final Random random;

    public Worker(@NonNull final String name, final long minDelay, final long maxDelay, @NonNull final Random random) {
        this.name = name;

        verify(minDelay >= 0, "minDelay must be >= 0");
        this.minDelay = minDelay;

        verify(maxDelay >= minDelay, "maxDelay must be >= minDelay");
        this.maxDelay = maxDelay;

        this.random = random;
    }

    public void compute() {
        var delay = random.nextLong(minDelay, maxDelay);
        var startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < delay) {
            doBusyWaiting();
        }

        var totalTime = System.currentTimeMillis() - startTime;
        log.info("worker {} done, total time: {}", name, totalTime);
    }

    private void doBusyWaiting() {
        var result = 0L;
        var iterations = 1000;
        while (iterations > 0) {
            result += 10;
            result *= random.nextLong();

            iterations--;
        }

        log.info("worker {} result: {}", name, result);
    }

}
