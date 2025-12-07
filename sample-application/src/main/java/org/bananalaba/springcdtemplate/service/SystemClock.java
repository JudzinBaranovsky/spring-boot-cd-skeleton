package org.bananalaba.springcdtemplate.service;

import java.time.Instant;

public class SystemClock {

    public Instant currentTime() {
        return Instant.ofEpochMilli(System.currentTimeMillis());
    }

    public void sleep(final long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
