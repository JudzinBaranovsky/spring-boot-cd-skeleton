package org.bananalaba.springcdtemplate.service;

import java.util.Map;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileTransformer {

    @NonNull
    private final SystemClock systemClock;

    public void process(@NonNull final String inputFileUrl,
                        @NonNull final String outputFilePath,
                        @NonNull final Map<String, String> parameters) {
        systemClock.sleep(1000);
    }

}
