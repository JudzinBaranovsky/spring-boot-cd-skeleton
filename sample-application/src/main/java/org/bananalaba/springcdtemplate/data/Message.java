package org.bananalaba.springcdtemplate.data;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class Message {

    @NonNull
    private final String text;

}
