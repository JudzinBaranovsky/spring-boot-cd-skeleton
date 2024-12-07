package org.bananalaba.springcdtemplate.data;

import java.util.Map;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BlobItem {

    @NonNull
    private final Map<String, String> metadata;
    @NonNull
    private final byte[] data;

}
