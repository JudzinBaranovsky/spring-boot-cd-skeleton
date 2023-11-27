package org.bananalaba.springcdtemplate.web;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.client.NumberServiceClient;
import org.bananalaba.springcdtemplate.logging.Loggable;
import org.bananalaba.springcdtemplate.model.SampleDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sample")
@RequiredArgsConstructor
@Loggable
public class SampleController {

    @NonNull
    @Value("${node.ip}")
    private final String nodeIp;
    @NonNull
    private final NumberServiceClient numberServiceClient;

    @GetMapping(path = "/hello", produces = "application/json")
    public SampleDto hello() {
        var luckyNumber = numberServiceClient.getRandomNumber();
        return new SampleDto("Hello, World", nodeIp, luckyNumber);
    }

}
