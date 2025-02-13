package org.bananalaba.springcdtemplate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@AllArgsConstructor
@Getter
@Jacksonized
@Builder
public class ErrorDto {

    private @NonNull final String message;

}
