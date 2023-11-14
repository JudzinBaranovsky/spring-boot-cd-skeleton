package org.bananalaba.springcdtemplate.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import org.bananalaba.springcdtemplate.client.NumberServiceClient;
import org.bananalaba.springcdtemplate.model.SampleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SampleControllerTest {

    private final NumberServiceClient numberServiceClient = mock(NumberServiceClient.class);
    private final SampleController controller = new SampleController("192.168.0.1", numberServiceClient);

    @BeforeEach
    void setUp() {
        reset(numberServiceClient);
    }

    @Test
    public void shouldGreet() {
        when(numberServiceClient.getRandomNumber()).thenReturn(50L);

        var actual = controller.hello();
        assertThat(actual).isEqualTo(new SampleDto("Hello, World", "192.168.0.1", 50L));
    }

}
