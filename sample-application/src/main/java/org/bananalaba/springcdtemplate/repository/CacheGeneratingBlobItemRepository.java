package org.bananalaba.springcdtemplate.repository;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.Cache;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.data.BlobItem;

@RequiredArgsConstructor
public class CacheGeneratingBlobItemRepository implements BlobItemRepository {

    @NonNull
    private final Cache<Long, BlobItem> cache;
    @NonNull
    private final BlobItemGenerator itemGenerator;

    @Override
    public BlobItem getById(long id) {
        try {
            return cache.get(id, itemGenerator::generate);
        } catch (ExecutionException e) {
            throw new DataStorageException("failed to load BLOB by id=" + id, e);
        }
    }

}
