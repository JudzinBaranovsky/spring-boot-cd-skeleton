package org.bananalaba.springcdtemplate.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.data.BlobItem;
import org.bananalaba.springcdtemplate.data.BlobItemSummary;
import org.bananalaba.springcdtemplate.repository.BlobItemRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlobItemService {

    @NonNull
    @Qualifier("imageRepository")
    private final BlobItemRepository imageRepository;

    @NonNull
    @Qualifier("textRepository")
    private final BlobItemRepository textRepository;

    @NonNull
    @Qualifier("videoRepository")
    private final BlobItemRepository videoRepository;

    public BlobItemSummary processImage(final long id) {
        return toSummary(imageRepository.getById(id));
    }

    public BlobItemSummary processText(final long id) {
        return toSummary(textRepository.getById(id));
    }

    public BlobItemSummary processVideo(final long id) {
        return toSummary(videoRepository.getById(id));
    }

    private BlobItemSummary toSummary(final BlobItem blobItem) {
        return new BlobItemSummary(blobItem.getMetadata(), blobItem.getData().length);
    }

}
