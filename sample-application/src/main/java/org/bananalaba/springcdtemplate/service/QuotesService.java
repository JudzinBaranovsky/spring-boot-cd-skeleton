package org.bananalaba.springcdtemplate.service;

import static com.google.common.base.Verify.verify;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.client.ZenQuotesApiClient;
import org.bananalaba.springcdtemplate.model.QuoteDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuotesService {

    @NonNull
    private final ZenQuotesApiClient quotesApiClient;

    public QuoteDto getRandomQuote() {
        var quotesList = quotesApiClient.getQuotes("random");

        verify(!quotesList.isEmpty(), "got an empty list of quotes which is unexpected");
        var randomQuote = quotesList.getFirst();

        return new QuoteDto(randomQuote.getQ());
    }

}
