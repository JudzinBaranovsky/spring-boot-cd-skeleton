package org.bananalaba.numberservice.service;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Random;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.micrometer.observation.annotation.Observed;
import lombok.NonNull;

@Observed(name = "number-generator")
public class NumberGenerator {

    private final Random random;
    private final int maxResponseLatency;

    @SuppressFBWarnings(value = {"PREDICTABLE_RANDOM", "EI_EXPOSE_REP2"}, justification = "these random values are not involved into any secure flows")
    public NumberGenerator(@NonNull Random random, int maxResponseLatency) {
        this.random = random;

        checkArgument(maxResponseLatency >= 0, "response latency cannot be < 0");
        this.maxResponseLatency = maxResponseLatency;
    }

    public long generateNumber() {
        var delay = (long) (random.nextDouble() * maxResponseLatency);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }

        return (long) (100 * random.nextDouble());
    }

}
