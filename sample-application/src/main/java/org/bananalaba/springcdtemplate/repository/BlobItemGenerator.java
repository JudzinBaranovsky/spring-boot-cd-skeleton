package org.bananalaba.springcdtemplate.repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.data.BlobItem;

@RequiredArgsConstructor
public class BlobItemGenerator {

    @NonNull
    private final MetadataGenerator metadataGenerator;
    @NonNull
    private final BlobGenerator blobGenerator;

    public BlobItem generate() {
        return new BlobItem(
            metadataGenerator.generate(),
            blobGenerator.generate()
        );
    }

}
