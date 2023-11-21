package org.bananalaba.numberservice.web;

import java.util.Random;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("api/v1/numbers")
@SuppressFBWarnings(value = "PREDICTABLE_RANDOM", justification = "these random values are not involved into any secure flows")
@Slf4j
public class NumbersController {

    private final Random random = new Random();

    @RequestMapping(method = RequestMethod.GET, path = "random", produces = "text/plain")
    public ResponseEntity<String> getRandomNumber() {
        log.info("generating a random number");
        var result = (long) (100 * random.nextDouble());
        return ResponseEntity.ok(String.valueOf(result));
    }

}
