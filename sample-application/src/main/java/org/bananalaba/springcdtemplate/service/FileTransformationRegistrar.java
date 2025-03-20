package org.bananalaba.springcdtemplate.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bananalaba.springcdtemplate.dto.FileTransformationStatusDto.StatusCode;
import org.bananalaba.springcdtemplate.model.FileTransformationTask;
import org.bananalaba.springcdtemplate.queue.FileTransformationQueue;
import org.bananalaba.springcdtemplate.storage.FileTransformationTaskStorage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileTransformationRegistrar {

    @NonNull
    private final FileTransformationQueue queue;
    @NonNull
    private final FileTransformationTaskStorage storage;

    @Scheduled(fixedDelay = 1000)
    public void registerNewTasks() {
        log.info("registering new tasks");
        queue.poll()
            .forEach(request -> {
                var taskDefinition = request.getDefinition();

                var registration = FileTransformationTask.builder()
                    .taskId(request.getTaskId())
                    .statusCode(StatusCode.IN_PROGRESS)
                    .inputFileUrl(taskDefinition.getInputFileUrl())
                    .outputFilePath(taskDefinition.getOutputFilePath())
                    .parameters(taskDefinition.getParameters())
                    .submissionTime(request.getSubmissionTime())
                    .updateTimestamp(request.getSubmissionTime())
                    .build();

                storage.create(registration);
                log.info("registered task {}", registration);
            });
    }

}
