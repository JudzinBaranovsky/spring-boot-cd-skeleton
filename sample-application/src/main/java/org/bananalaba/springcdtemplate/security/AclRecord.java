package org.bananalaba.springcdtemplate.security;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class AclRecord {

    @NonNull
    private final String subjectType;
    @NonNull
    private final String subjectKey;

    @NonNull
    private final List<String> editorIds;

}
