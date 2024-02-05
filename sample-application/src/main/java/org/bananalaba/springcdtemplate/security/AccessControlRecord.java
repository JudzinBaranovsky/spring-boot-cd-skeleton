package org.bananalaba.springcdtemplate.security;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccessControlRecord {

    @NonNull
    private final String ownerId;
    @NonNull
    private final String messageKey;

}
