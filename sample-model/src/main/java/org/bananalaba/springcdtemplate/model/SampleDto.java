package org.bananalaba.springcdtemplate.model;

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
public class SampleDto {

    @NonNull
    private final String message;
    @NonNull
    private final String nodeIp;

}
