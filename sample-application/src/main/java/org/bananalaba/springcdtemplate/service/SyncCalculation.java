package org.bananalaba.springcdtemplate.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SyncCalculation {

    @NonNull
    private final Worker worker;

    public void calculate() {
        worker.compute();
    }

}
