package org.bananalaba.springcdtemplate.web;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.logging.Loggable;
import org.bananalaba.springcdtemplate.model.QuoteDto;
import org.bananalaba.springcdtemplate.service.QuotesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/quotes")
@RequiredArgsConstructor
@Loggable
public class SampleController {

    @NonNull
    private final QuotesService quotesService;

    @GetMapping(path = "/random", produces = "application/json")
    public QuoteDto hello() {
        return quotesService.getRandomQuote();
    }

}
