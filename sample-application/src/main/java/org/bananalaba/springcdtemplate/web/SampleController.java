package org.bananalaba.springcdtemplate.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.logging.Loggable;
import org.bananalaba.springcdtemplate.model.SampleDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/status")
@RequiredArgsConstructor
@Loggable
public class SampleController {

    @NonNull
    @Value("${node.ip}")
    private final String nodeIp;

    @GetMapping(produces = "application/json")
    @Operation(summary = "Get status data", security = @SecurityRequirement(name = "bearerAuth"))
    public SampleDto getStatus() {
        return new SampleDto("status: up", nodeIp);
    }

}
