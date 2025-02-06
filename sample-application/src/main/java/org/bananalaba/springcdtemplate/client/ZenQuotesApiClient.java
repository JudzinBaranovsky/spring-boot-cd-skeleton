package org.bananalaba.springcdtemplate.client;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface ZenQuotesApiClient {

    @RequestMapping(method = RequestMethod.GET, path = "/", consumes = "application/json")
    List<ZenQuoteDto> getQuotes(@RequestParam String api);

}
