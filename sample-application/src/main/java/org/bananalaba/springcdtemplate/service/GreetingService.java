package org.bananalaba.springcdtemplate.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.model.SampleDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GreetingService {

    @NonNull
    @Value("${node.ip}")
    private final String nodeIp;

    private final TipsService tipsService;

    public SampleDto greet() {
        var tip = tipsService.getTip();
        return new SampleDto("Hello, World! Your tip: " + tip, nodeIp);
    }

}
