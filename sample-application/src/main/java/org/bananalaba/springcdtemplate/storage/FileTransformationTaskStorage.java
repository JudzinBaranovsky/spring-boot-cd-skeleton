package org.bananalaba.springcdtemplate.storage;

import java.time.Instant;
import java.util.Optional;

import org.bananalaba.springcdtemplate.model.FileTransformationTask;

public interface FileTransformationTaskStorage {

    FileTransformationTask getByTaskId(final String id);

    void create(final FileTransformationTask status);

    Optional<FileTransformationTask> takeTask(final Instant updatedBefore, final Instant currentTime, final String workerId);

    void completeTask(final String taskId, final Instant currentTime);

}
