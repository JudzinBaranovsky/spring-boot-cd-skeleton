package org.bananalaba.springcdtemplate.storage;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.NonNull;
import org.bananalaba.springcdtemplate.dto.FileTransformationStatusDto.StatusCode;
import org.bananalaba.springcdtemplate.model.FileTransformationTask;
import org.springframework.stereotype.Component;

@Component
public class InMemoryFileTransformationTaskStorage implements FileTransformationTaskStorage {

    private final Lock lock = new ReentrantLock();
    private final Map<String, FileTransformationTask> tasks = new ConcurrentHashMap<>();

    @Override
    public FileTransformationTask getByTaskId(@NonNull final String id) {
        return tasks.get(id);
    }

    @Override
    public void create(@NonNull final FileTransformationTask status) {
        lock.lock();
        try {
            if (tasks.containsKey(status.getTaskId())) {
                throw new IllegalArgumentException("task with id " + status.getTaskId() + " already exists");
            }

            tasks.put(status.getTaskId(), status);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Optional<FileTransformationTask> takeTask(@NonNull final Instant updatedBefore,
                                                     @NonNull final Instant currentTime,
                                                     @NonNull final String workerId) {
        lock.lock();

        var result = tasks.values()
            .stream()
            .filter(task -> (task.getActiveWorkerId() == null) || task.getUpdateTimestamp().isBefore(updatedBefore))
            .filter(task -> task.getStatusCode() == StatusCode.IN_PROGRESS)
            .findAny()
            .map(task -> task.take(currentTime, workerId));

        lock.unlock();
        return result;
    }

    @Override
    public void completeTask(String taskId, Instant currentTime) {
        lock.lock();
        try {
            var task = tasks.get(taskId);
            if (task == null) {
                throw new IllegalArgumentException("task with id " + taskId + " not found");
            }

            var completed = task.complete(currentTime);
            tasks.put(taskId, completed);
        } finally {
            lock.unlock();
        }
    }

}
