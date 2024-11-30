package org.bananalaba.springcdtemplate.web;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.logging.Loggable;
import org.bananalaba.springcdtemplate.model.DataItemDto;
import org.bananalaba.springcdtemplate.service.CalculationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/calculation")
@RequiredArgsConstructor
@Loggable
public class CalculationController {

    @NonNull
    private final CalculationService calculationService;

    @GetMapping(path = "/", produces = "application/json")
    public DataItemDto calculate(final @RequestParam("p") String parameter) {
        return calculationService.calculate(parameter);
    }

}
