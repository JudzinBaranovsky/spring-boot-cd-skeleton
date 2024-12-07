package org.bananalaba.springcdtemplate.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.bananalaba.springcdtemplate.model.DataItemDto;
import org.bananalaba.springcdtemplate.service.CalculationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CalculationControllerTest {

    @Mock
    private CalculationService calculationService;

    @InjectMocks
    private CalculationController controller;

    @Test
    public void shouldCalculate() {
        var expected = new DataItemDto("result");
        when(calculationService.calculate("abc")).thenReturn(expected);

        var actual = controller.calculate("abc");
        assertThat(actual).isEqualTo(expected);
    }

}
