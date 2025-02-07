package org.bananalaba.springcdtemplate.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.bananalaba.springcdtemplate.model.QuoteDto;
import org.bananalaba.springcdtemplate.service.QuotesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SampleControllerTest {

    @Mock
    private QuotesService quotesService;
    @InjectMocks
    private SampleController controller;

    @Test
    public void shouldGreet() {
        var expected = new QuoteDto("Hello, World");
        when(quotesService.getRandomQuote()).thenReturn(expected);

        var actual = controller.hello();
        assertThat(actual).isEqualTo(expected);
    }

}
