package org.bananalaba.springcdtemplate.web;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.dto.FileTransformationDefinitionDto;
import org.bananalaba.springcdtemplate.dto.FileTransformationStatusDto;
import org.bananalaba.springcdtemplate.service.FileTransformationManager;
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
    private final FileTransformationManager service;

    @GetMapping(path = "/status/{id}", produces = "application/json")
    public FileTransformationStatusDto getStatus(@NonNull @PathVariable final String id) {
        return service.getStatus(id);
    }

    @PostMapping(path = "/submit", produces = "application/json", consumes = "application/json")
    public FileTransformationStatusDto submit(@NonNull @RequestBody final FileTransformationDefinitionDto definition) {
        return service.submit(definition);
    }

}
