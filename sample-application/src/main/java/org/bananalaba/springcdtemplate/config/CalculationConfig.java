package org.bananalaba.springcdtemplate.config;

import java.util.Random;
import java.util.concurrent.Executors;

import org.bananalaba.springcdtemplate.service.AsyncCalculation;
import org.bananalaba.springcdtemplate.service.SyncCalculation;
import org.bananalaba.springcdtemplate.service.Worker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CalculationConfig {

    @Bean
    public SyncCalculation syncCalculation(@Value("${sync.worker.minDelaysMs}") final long minDelaysMs,
                                           @Value("${sync.worker.maxDelaysMs}") final long maxDelayMs) {
        var worker = new Worker("sync worker", minDelaysMs, maxDelayMs, new Random());
        return new SyncCalculation(worker);
    }

    @Bean
    public AsyncCalculation asyncCalculation(@Value("${async.threads}") final int threads,
                                             @Value("${async.worker.minDelaysMs}") final long minDelaysMs,
                                             @Value("${async.worker.maxDelaysMs}") final long maxDelayMs) {
        var executor = Executors.newFixedThreadPool(threads);
        var worker = new Worker("async worker", minDelaysMs, maxDelayMs, new Random());

        return new AsyncCalculation(worker, executor);
    }

}
