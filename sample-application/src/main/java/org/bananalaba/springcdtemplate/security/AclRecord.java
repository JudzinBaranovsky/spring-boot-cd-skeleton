package org.bananalaba.springcdtemplate.security;

import java.util.List;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class AclRecord {

    private final String subjectType;
    private final String subjectKey;

    private final List<String> editorIds;

    @Builder
    private AclRecord(@NonNull String subjectType, @NonNull String subjectKey, @NonNull List<String> editorIds) {
        this.subjectType = subjectType;
        this.subjectKey = subjectKey;
        this.editorIds = ImmutableList.copyOf(editorIds);
    }

}
