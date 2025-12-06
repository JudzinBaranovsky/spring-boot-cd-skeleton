package org.bananalaba.springcdtemplate.web;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bananalaba.springcdtemplate.logging.Loggable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sample")
@Slf4j
@RequiredArgsConstructor
@Loggable
public class SampleController {

    @GetMapping(path = "/hello", produces = "application/json")
    public Map<String, String> hello() {
        log.info("received a hello request");
        return Map.of("message", "Hello, World!");
    }

}
