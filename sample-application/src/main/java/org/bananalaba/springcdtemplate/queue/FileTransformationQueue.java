package org.bananalaba.springcdtemplate.queue;

import org.bananalaba.springcdtemplate.dto.FileTransformationRequest;

public interface FileTransformationQueue {

    void send(final FileTransformationRequest request);

}
