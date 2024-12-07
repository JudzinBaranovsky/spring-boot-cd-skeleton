package org.bananalaba.springcdtemplate.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MetadataGenerator {

    @NonNull
    private final Random random;
    @NonNull
    private final Map<String, List<String>> samples;

    public Map<String, String> generate() {
        var result = new HashMap<String, String>();
        samples.forEach((key, values) -> result.put(
            key,
            values.get(random.nextInt(values.size()))
        ));

        return result;
    }

}
