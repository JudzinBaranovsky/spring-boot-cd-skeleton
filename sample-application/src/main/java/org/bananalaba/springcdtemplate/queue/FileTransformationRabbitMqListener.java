package org.bananalaba.springcdtemplate.queue;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.dto.FileTransformationRequest;
import org.bananalaba.springcdtemplate.dto.FileTransformationStatusDto;
import org.bananalaba.springcdtemplate.dto.FileTransformationStatusDto.StatusCode;
import org.bananalaba.springcdtemplate.service.FileTransformer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileTransformationRabbitMqListener {

    @NonNull
    private final FileTransformer transformer;

    @RabbitListener(queues = "${fileTransformation.amqp.queue.name}")
    @SendTo
    public FileTransformationStatusDto handle(@NonNull final FileTransformationRequest request) {
        var definition = request.getDefinition();

        try {
            transformer.process(definition.getInputFileUrl(), definition.getOutputFilePath(), definition.getParameters());
            return FileTransformationStatusDto.builder()
                .taskId(request.getTaskId())
                .status(StatusCode.COMPLETED)
                .build();
        } catch (Exception e) {
            return FileTransformationStatusDto.builder()
                .taskId(request.getTaskId())
                .status(StatusCode.FAILED)
                .build();
        }
    }

}
