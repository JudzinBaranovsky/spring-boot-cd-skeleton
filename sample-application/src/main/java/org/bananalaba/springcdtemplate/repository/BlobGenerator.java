package org.bananalaba.springcdtemplate.repository;

import static org.apache.commons.lang3.Validate.isTrue;

import java.util.UUID;

public class BlobGenerator {

    private final int growthFactor;

    private final int minIterations;
    private final int maxIterations;

    public BlobGenerator(final int growthFactor, final int minIterations, final int maxIterations) {
        isTrue(growthFactor > 0, "growthFactor must be > 0");
        this.growthFactor = growthFactor;

        isTrue(minIterations > 0, "minIterations must be > 0");
        this.minIterations = minIterations;

        isTrue(maxIterations >= minIterations, "maxIterations must be > minIterations");
        this.maxIterations = maxIterations;
    }

    public byte[] generate() {
        var base = UUID.randomUUID().toString();
        var iterations = (int) (Math.random() * (maxIterations - minIterations)) + minIterations;
        var builder = new StringBuilder(base);
        for (var i = 0; i < iterations - 1; i++) {
            var increment = builder.toString().repeat(growthFactor);
            builder.append(increment);
        }

        return builder.toString().getBytes();
    }

}
