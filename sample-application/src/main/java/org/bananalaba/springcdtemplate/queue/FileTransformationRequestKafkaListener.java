package org.bananalaba.springcdtemplate.queue;

import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.dto.FileTransformationRequest;
import org.bananalaba.springcdtemplate.service.FileTransformationRegistrar;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileTransformationRequestKafkaListener {

    @NonNull
    private final FileTransformationRegistrar registrar;

    @KafkaListener(
        topics = "${fileTransformation.kafka.topic.name}",
        containerFactory = "fileTransformationRequestKafkaListenerContainerFactory"
    )
    public void handle(@NonNull final List<FileTransformationRequest> requests) {
        registrar.registerNewTasks(requests);
    }

}
