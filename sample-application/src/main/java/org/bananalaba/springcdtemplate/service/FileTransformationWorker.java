package org.bananalaba.springcdtemplate.service;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bananalaba.springcdtemplate.model.FileTransformationTask;
import org.bananalaba.springcdtemplate.storage.FileTransformationTaskStorage;

@RequiredArgsConstructor
@Slf4j
public class FileTransformationWorker {

    @NonNull
    private final FileTransformationTaskStorage storage;

    @NonNull
    @Getter
    private final String id;
    @NonNull
    private final SystemClock systemClock;
    private final long minTaskRetryDelayMs;

    @NonNull
    private final FileTransformer transformer;

    public void run() {
        var startTime = systemClock.currentTime();
        log.info("worker {} triggered at {}", id, startTime);

        var task = storage.takeTask(startTime.minusMillis(minTaskRetryDelayMs), startTime, id);

        if (task.isPresent()) {
            log.info("worker {} took task {}", id, task.get().getTaskId());
            process(task.get());
        } else {
            log.info("worker {} has nothing to do", id);
        }
    }

    private void process(final FileTransformationTask task) {
        transformer.process(task.getInputFileUrl(), task.getOutputFilePath(), task.getParameters());

        var endTime = systemClock.currentTime();
        storage.completeTask(task.getTaskId(), endTime);

        log.info("worker {} completed task {}", id, task.getTaskId());
    }

}
