package org.bananalaba.springcdtemplate.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AsyncCalculation {

    @NonNull
    private final Worker worker;
    @NonNull
    private final ExecutorService executor;

    @NonNull
    public Future<Void> calculate() {
        return executor.submit(() -> {
            worker.compute();
            return null;
        });
    }

}
