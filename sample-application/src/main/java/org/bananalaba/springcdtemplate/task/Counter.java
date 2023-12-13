package org.bananalaba.springcdtemplate.task;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Component;

@Component
public class Counter {

    private final Map<String, Integer> data = new ConcurrentHashMap<>();

    public void reset() {
        data.clear();
    }

    public void increment(String prefix) {
        data.compute(prefix + "-" + Thread.currentThread().getName(), (k, v) -> (v == null) ? 1 : v + 1);
    }

    public Map<String, Integer> getData() {
        return ImmutableMap.copyOf(data);
    }

}
