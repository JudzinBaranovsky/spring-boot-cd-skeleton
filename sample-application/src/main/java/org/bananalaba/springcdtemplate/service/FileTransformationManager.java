package org.bananalaba.springcdtemplate.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bananalaba.springcdtemplate.dto.FileTransformationDefinitionDto;
import org.bananalaba.springcdtemplate.dto.FileTransformationRequest;
import org.bananalaba.springcdtemplate.dto.FileTransformationStatusDto;
import org.bananalaba.springcdtemplate.dto.FileTransformationStatusDto.StatusCode;
import org.bananalaba.springcdtemplate.queue.FileTransformationQueue;
import org.bananalaba.springcdtemplate.storage.FileTransformationTaskStorage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileTransformationManager {

    @NonNull
    private final FileTransformationTaskStorage storage;
    @NonNull
    private final FileTransformationQueue queue;
    @NonNull
    private final TaskIdGenerator taskIdGenerator;
    @NonNull
    private final SystemClock systemClock;

    public FileTransformationStatusDto submit(@NonNull final FileTransformationDefinitionDto definition) {
        var now = systemClock.currentTime();
        var taskId = taskIdGenerator.get();
        var request = new FileTransformationRequest(taskId, definition, now);

        queue.send(request);
        log.info("submitted request {}", request);

        return FileTransformationStatusDto.builder()
            .taskId(taskId)
            .status(StatusCode.IN_PROGRESS)
            .build();
    }

    public FileTransformationStatusDto getStatus(@NonNull final String taskId) {
        var status = storage.getByTaskId(taskId);
        if (status == null) {
            throw new IllegalArgumentException("task with id " + taskId + " not found");
        }

        return FileTransformationStatusDto.builder()
            .taskId(taskId)
            .status(status.getStatusCode())
            .build();
    }

}
