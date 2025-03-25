package org.bananalaba.springcdtemplate.dto;

import java.time.Instant;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@RequiredArgsConstructor
@Jacksonized
@Builder
@EqualsAndHashCode
@ToString
public class FileTransformationRequest {

    @NonNull
    private final String taskId;
    @NonNull
    private final FileTransformationDefinitionDto definition;
    @NonNull
    private final Instant submissionTime;

}
