package org.bananalaba.springcdtemplate.web;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.dto.FileTransformationDefinitionDto;
import org.bananalaba.springcdtemplate.dto.FileTransformationStatusDto;
import org.bananalaba.springcdtemplate.service.FileTransformationDispatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/file-transformations")
@RequiredArgsConstructor
public class FileTransformationController {

    @NonNull
    private final FileTransformationDispatcher dispatcher;

    @GetMapping(path = "/status/{id}", produces = "application/json")
    public FileTransformationStatusDto getStatus(@NonNull @PathVariable final String id) {
        return dispatcher.getStatus(id);
    }

    @PostMapping(path = "/submitAsync", produces = "application/json", consumes = "application/json")
    public FileTransformationStatusDto submitAsync(@NonNull @RequestBody final FileTransformationDefinitionDto definition) {
        return dispatcher.submitAsync(definition);
    }

    @PostMapping(path = "/submitSync", produces = "application/json", consumes = "application/json")
    public FileTransformationStatusDto submitSync(@NonNull @RequestBody final FileTransformationDefinitionDto definition) {
        return dispatcher.submitSync(definition);
    }

}
