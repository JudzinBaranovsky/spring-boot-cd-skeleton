package org.bananalaba.springcdtemplate.queue;

import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.bananalaba.springcdtemplate.dto.FileTransformationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaFileTransformationQueue implements FileTransformationQueue {

    @NonNull
    @Value("${fileTransformation.kafka.topic.name}")
    private final String topicName;
    @NonNull
    private final KafkaTemplate<String, FileTransformationRequest> kafkaTemplate;

    @Override
    public void send(@NonNull final FileTransformationRequest request) {
        var record = new ProducerRecord<>(topicName, request.getTaskId(), request);
        kafkaTemplate.send(record);
    }

    @Override
    public List<FileTransformationRequest> poll() {
        throw new UnsupportedOperationException();
    }

}
