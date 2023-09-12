package org.bananalaba.springcdtemplate.web;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bananalaba.springcdtemplate.model.SampleDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sample")
@Slf4j
@RequiredArgsConstructor
public class SampleController {

    @NonNull
    @Value("${node.ip}")
    private final String nodeIp;

    @GetMapping(path = "/hello", produces = "application/json")
    public SampleDto hello() {
        log.info("received a hello request");
        return new SampleDto("Hello, World from IP: " + nodeIp);
    }

}
