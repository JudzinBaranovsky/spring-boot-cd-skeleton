package org.bananalaba.springcdtemplate.data;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@RequiredArgsConstructor
@Getter
@Jacksonized
@Builder
public class BlobItemSummary {

    @NonNull
    private final Map<String, String> metadata;
    private final long sizeInBytes;

}
