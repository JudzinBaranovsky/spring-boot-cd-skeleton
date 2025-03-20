package org.bananalaba.springcdtemplate.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.bananalaba.springcdtemplate.dto.FileTransformationRequest;
import org.junit.jupiter.api.Test;

public class SampleControllerTest {

    private final FileTransformationController controller = new FileTransformationController("192.168.0.1");

    @Test
    public void shouldGreet() {
        var actual = controller.hello();
        assertThat(actual).isEqualTo(new FileTransformationRequest("Hello, World", "192.168.0.1"));
    }

}
