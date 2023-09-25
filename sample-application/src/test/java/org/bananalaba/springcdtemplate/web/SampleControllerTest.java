package org.bananalaba.springcdtemplate.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.bananalaba.springcdtemplate.model.SampleDto;
import org.junit.jupiter.api.Test;

public class SampleControllerTest {

    private final SampleController controller = new SampleController("192.168.0.1");

    @Test
    public void shouldGreet() {
        var actual = controller.hello();
        assertThat(actual).isEqualTo(new SampleDto("Hello, World", "192.168.0.1"));
    }

}
