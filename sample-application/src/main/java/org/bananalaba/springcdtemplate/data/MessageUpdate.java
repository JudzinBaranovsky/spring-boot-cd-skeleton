package org.bananalaba.springcdtemplate.data;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
@RequiredArgsConstructor
public class MessageUpdate {

    @NonNull
    private final String text;

}
