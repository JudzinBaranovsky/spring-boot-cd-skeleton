package org.bananalaba.springcdtemplate.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Getter
@RequiredArgsConstructor
@Jacksonized
@Builder
@EqualsAndHashCode
public class FileTransformationStatusDto {

    @NonNull
    private final String taskId;
    @NonNull
    private final StatusCode status;

    public enum StatusCode {
        IN_PROGRESS,
        COMPLETED,
        FAILED
    }

}
