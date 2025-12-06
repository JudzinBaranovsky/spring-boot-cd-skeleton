package org.bananalaba.springcdtemplate.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class SampleControllerTest {

    private final SampleController controller = new SampleController();

    @Test
    public void shouldGreet() {
        var actual = controller.hello();
        assertThat(actual).isEqualTo(Map.of("message", "Hello, World!"));
    }

}
