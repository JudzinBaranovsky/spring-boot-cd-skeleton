package org.bananalaba.perf.data.service;

import static com.google.common.base.Verify.verify;

import java.util.Random;
import java.util.function.Supplier;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.bananalaba.springcdtemplate.model.DataItemDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataService {

    private final long minLatency;
    private final long maxLatency;

    private final Random random;
    private final Supplier<String> dataGenerator;

    public DataService(@Value("${dataService.minLatency}") final long minLatency,
                       @Value("${dataService.maxLatency}") final long maxLatency,
                       @NonNull final Random random,
                       @NonNull final Supplier<String> dataGenerator) {
        verify(minLatency >= 0, "minLatency must be >= 0");
        this.minLatency = minLatency;

        verify(maxLatency >= minLatency, "maxLatency must be >= minLatency");
        this.maxLatency = maxLatency;

        this.random = random;
        this.dataGenerator = dataGenerator;
    }

    public DataItemDto getData(@NonNull final String parameter) {
        var delay = random.nextLong(minLatency, maxLatency);
        var startTime = System.currentTimeMillis();
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DataServiceInternalException("could not simulate latency", e);
        }

        var totalTime = System.currentTimeMillis() - startTime;
        log.info("data for {} retrieved, total time: {}", parameter, totalTime);

        var content = dataGenerator.get();
        return new DataItemDto(content);
    }

}
