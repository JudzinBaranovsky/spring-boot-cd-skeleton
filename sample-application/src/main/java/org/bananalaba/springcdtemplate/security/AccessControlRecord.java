package org.bananalaba.springcdtemplate.security;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class AccessControlRecord {

    @NonNull
    private final String ownerId;
    @NonNull
    private final String messageKey;

}
