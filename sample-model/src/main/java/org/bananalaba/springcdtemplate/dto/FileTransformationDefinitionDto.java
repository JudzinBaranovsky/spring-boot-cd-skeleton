package org.bananalaba.springcdtemplate.dto;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class FileTransformationDefinitionDto {

    @NonNull
    private final String inputFileUrl;
    @NonNull
    private final String outputFilePath;
    @NonNull
    private final Map<String, String> parameters;

}
