package org.bananalaba.springcdtemplate.proxy.web;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.model.SampleDto;
import org.bananalaba.springcdtemplate.proxy.service.StatusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/proxy/status")
@RequiredArgsConstructor
public class ProxyController {

    @NonNull
    private final StatusService statusService;

    @GetMapping(produces = "application/json")
    public SampleDto getStatus() {
        return statusService.getStatus();
    }

}
