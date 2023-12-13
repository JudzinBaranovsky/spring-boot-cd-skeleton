package org.bananalaba.springcdtemplate.task;

import org.springframework.stereotype.Component;

@Component
public class Timer {

    public void countDown(long millis) throws InterruptedException {
        Thread.sleep(millis);
    }

}
