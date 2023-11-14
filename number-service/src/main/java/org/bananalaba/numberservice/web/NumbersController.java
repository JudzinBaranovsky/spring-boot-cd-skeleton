package org.bananalaba.numberservice.web;

import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("api/v1/numbers")
public class NumbersController {

    private final Random random = new Random();

    @RequestMapping(method = RequestMethod.GET, path = "random", produces = "text/plain")
    public ResponseEntity<String> getRandomNumber() {
        var result = (long) (100 * random.nextDouble());
        return ResponseEntity.ok(String.valueOf(result));
    }

}
