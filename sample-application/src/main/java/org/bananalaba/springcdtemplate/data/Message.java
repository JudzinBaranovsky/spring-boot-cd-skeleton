package org.bananalaba.springcdtemplate.data;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class Message implements OwnershipSubject {

    @NonNull
    private final String text;
    @NonNull
    private final String ownerId;

}
