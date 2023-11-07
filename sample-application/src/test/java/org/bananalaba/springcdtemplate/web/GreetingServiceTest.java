package org.bananalaba.springcdtemplate.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.bananalaba.springcdtemplate.model.SampleDto;
import org.bananalaba.springcdtemplate.service.GreetingService;
import org.bananalaba.springcdtemplate.service.TipsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GreetingServiceTest {

    private GreetingService service;

    @Mock
    private TipsService tipsService;

    @BeforeEach
    void setUp() {
        service = new GreetingService("192.168.0.1", tipsService);
    }

    @Test
    public void shouldGreet() {
        when(tipsService.getTip()).thenReturn("some tip");

        var actual = service.greet();
        assertThat(actual).isEqualTo(new SampleDto("Hello, World! Your tip: some tip", "192.168.0.1"));
    }

}
