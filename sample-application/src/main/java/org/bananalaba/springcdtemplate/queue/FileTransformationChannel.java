package org.bananalaba.springcdtemplate.queue;

import org.bananalaba.springcdtemplate.dto.FileTransformationRequest;
import org.bananalaba.springcdtemplate.dto.FileTransformationStatusDto;

public interface FileTransformationChannel {

    FileTransformationStatusDto sendAndReceive(final FileTransformationRequest request);

}
