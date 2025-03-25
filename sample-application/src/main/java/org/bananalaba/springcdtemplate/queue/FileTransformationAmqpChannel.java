package org.bananalaba.springcdtemplate.queue;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.dto.FileTransformationRequest;
import org.bananalaba.springcdtemplate.dto.FileTransformationStatusDto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileTransformationAmqpChannel implements FileTransformationChannel {

    private static final ParameterizedTypeReference<FileTransformationStatusDto> STATUS_TYPE =
        ParameterizedTypeReference.forType(FileTransformationStatusDto.class);

    @Value("${fileTransformation.amqp.routing.key}")
    private final String routingKey;
    @NonNull
    private final AmqpTemplate amqpTemplate;

    @Override
    public FileTransformationStatusDto sendAndReceive(@NonNull final FileTransformationRequest request) {
        return amqpTemplate.convertSendAndReceiveAsType(routingKey, request, STATUS_TYPE);
    }

}
