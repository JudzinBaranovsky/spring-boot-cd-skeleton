package org.bananalaba.springcdtemplate.model;

import static org.apache.commons.lang3.Validate.isTrue;

import java.time.Instant;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.bananalaba.springcdtemplate.dto.FileTransformationStatusDto.StatusCode;

@Getter
@Builder(toBuilder = true)
@ToString
public class FileTransformationTask {

    @NonNull
    private final String taskId;
    private final String activeWorkerId;
    @NonNull
    private final Instant updateTimestamp;
    @NonNull
    private final StatusCode statusCode;

    @NonNull
    private final String inputFileUrl;
    @NonNull
    private final String outputFilePath;
    @NonNull
    private final Map<String, String> parameters;
    @NonNull
    private final Instant submissionTime;

    public FileTransformationTask take(@NonNull final Instant currentTime,
                                       @NonNull final String workerId) {
        isTrue(currentTime.isAfter(updateTimestamp), "cannot update task in the past");
        return toBuilder()
            .activeWorkerId(workerId)
            .updateTimestamp(currentTime)
            .build();
    }

    public FileTransformationTask complete(@NonNull final Instant currentTime) {
        isTrue(currentTime.isAfter(updateTimestamp), "cannot update task in the past");
        return toBuilder()
            .statusCode(StatusCode.COMPLETED)
            .updateTimestamp(currentTime)
            .build();
    }

}
