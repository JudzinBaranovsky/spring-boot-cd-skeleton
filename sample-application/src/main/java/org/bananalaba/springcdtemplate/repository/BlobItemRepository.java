package org.bananalaba.springcdtemplate.repository;

import org.bananalaba.springcdtemplate.data.BlobItem;

public interface BlobItemRepository {

    BlobItem getById(long id);

}
