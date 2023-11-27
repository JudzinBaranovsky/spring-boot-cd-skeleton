package org.bananalaba.numberservice.web;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bananalaba.numberservice.service.NumberGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("api/v1/numbers")
@Slf4j
@RequiredArgsConstructor
public class NumbersController {

    @NonNull
    private final NumberGenerator generator;

    @RequestMapping(method = RequestMethod.GET, path = "random", produces = "text/plain")
    public ResponseEntity<String> getRandomNumber() {
        log.info("generating a random number");
        var result = generator.generateNumber();
        return ResponseEntity.ok(String.valueOf(result));
    }

}
