package org.bananalaba.springcdtemplate.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.bananalaba.springcdtemplate.model.DataItemDto;
import org.junit.jupiter.api.Test;

public class SampleControllerTest {

    private final CalculationController controller = new CalculationController("192.168.0.1");

    @Test
    public void shouldGreet() {
        var actual = controller.hello();
        assertThat(actual).isEqualTo(new DataItemDto("Hello, World", "192.168.0.1"));
    }

}
