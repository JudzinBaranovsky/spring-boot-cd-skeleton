package org.bananalaba.springcdtemplate.config;

import static org.apache.commons.lang3.Validate.isTrue;

import java.time.Duration;

import org.bananalaba.springcdtemplate.service.FileTransformationWorker;
import org.bananalaba.springcdtemplate.service.FileTransformer;
import org.bananalaba.springcdtemplate.service.SystemClock;
import org.bananalaba.springcdtemplate.storage.FileTransformationTaskStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class FileTransformationWorkerModule {

    private final int poolSize;
    private final long minTaskRetryDelayMs;
    private final long workerTriggerRateMs;

    public FileTransformationWorkerModule(@Value("${fileTransformation.worker.poolSize:1}") final int poolSize,
                                          @Value("${transformation.minTaskRetryDelayMs:3600000}") final long minTaskRetryDelayMs,
                                          @Value("${fileTransformation.worker.rateMs:5000}") final long workerTriggerRateMs) {
        isTrue(poolSize > 0, "poolSize must be greater than 0");
        this.poolSize = poolSize;

        isTrue(minTaskRetryDelayMs > 0, "minTaskRetryDelay must be greater than 0");
        this.minTaskRetryDelayMs = minTaskRetryDelayMs;

        isTrue(workerTriggerRateMs > 0, "workerTriggerRate must be greater than 0");
        this.workerTriggerRateMs = workerTriggerRateMs;
    }

    @Bean
    public TaskScheduler fileTransformationTaskScheduler(final FileTransformationTaskStorage storage,
                                                         final SystemClock systemClock,
                                                         final FileTransformer transformer) {
        var scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(poolSize);
        scheduler.initialize();

        for (int i = 0; i < poolSize; i++) {
            var worker = new FileTransformationWorker(
                storage,
                "ft-worker-" + i,
                systemClock,
                minTaskRetryDelayMs,
                transformer
            );
            scheduler.scheduleAtFixedRate(worker::run, Duration.ofMillis(workerTriggerRateMs));
        }

        return scheduler;
    }

}
