package org.bananalaba.springcdtemplate.service;

import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bananalaba.springcdtemplate.dto.FileTransformationRequest;
import org.bananalaba.springcdtemplate.dto.FileTransformationStatusDto.StatusCode;
import org.bananalaba.springcdtemplate.model.FileTransformationTask;
import org.bananalaba.springcdtemplate.storage.FileTransformationTaskStorage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileTransformationRegistrar {

    @NonNull
    private final FileTransformationTaskStorage storage;

    public void registerNewTasks(@NonNull final List<FileTransformationRequest> requests) {
        log.info("registering new tasks");
        requests.forEach(request -> {
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
