package org.bananalaba.springcdtemplate.web;

import org.bananalaba.springcdtemplate.model.SampleDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sample")
public class SampleController {

    @GetMapping(path = "/hello", produces = "application/json")
    public SampleDto hello() {
        return new SampleDto("Hello, World");
    }

}
