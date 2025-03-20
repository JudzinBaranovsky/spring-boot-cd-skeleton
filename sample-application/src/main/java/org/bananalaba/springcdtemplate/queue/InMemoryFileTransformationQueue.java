package org.bananalaba.springcdtemplate.queue;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import lombok.NonNull;
import org.bananalaba.springcdtemplate.dto.FileTransformationRequest;

public class InMemoryFileTransformationQueue implements FileTransformationQueue {

    private final Queue<FileTransformationRequest> queue = new ConcurrentLinkedQueue<>();

    @Override
    public void send(@NonNull final FileTransformationRequest request) {
        queue.offer(request);
    }

    @Override
    public List<FileTransformationRequest> poll() {
        var request = queue.poll();
        return request == null ? List.of() : List.of(request);
    }
}
