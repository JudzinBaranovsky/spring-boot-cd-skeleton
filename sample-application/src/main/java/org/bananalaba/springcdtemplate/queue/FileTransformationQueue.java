package org.bananalaba.springcdtemplate.queue;

import java.util.List;

import org.bananalaba.springcdtemplate.dto.FileTransformationRequest;

public interface FileTransformationQueue {

    void send(final FileTransformationRequest request);

    List<FileTransformationRequest> poll();

}
