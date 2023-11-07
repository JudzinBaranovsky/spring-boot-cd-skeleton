package org.bananalaba.springcdtemplate.web;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bananalaba.springcdtemplate.model.SampleDto;
import org.bananalaba.springcdtemplate.service.GreetingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sample")
@Slf4j
@RequiredArgsConstructor
public class SampleController {

    @NonNull
    private final GreetingService greetingService;

    @GetMapping(path = "/hello", produces = "application/json")
    public SampleDto hello() {
        log.info("received a hello request");
        return greetingService.greet();
    }

}
