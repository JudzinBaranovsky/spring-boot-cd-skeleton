package org.bananalaba.springcdtemplate.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Getter
@RequiredArgsConstructor
@Jacksonized
@Builder
public class SampleDto {

    @NonNull
    private final String message;

}
