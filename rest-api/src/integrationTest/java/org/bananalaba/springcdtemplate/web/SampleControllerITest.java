package org.bananalaba.springcdtemplate.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SampleControllerITest {

    static {
        System.setProperty("node.ip", "192.168.0.1");
    }

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldGreet() throws Exception {
        var actual = mvc.perform(get("/api/v1/sample/hello")).andReturn().getResponse();

        assertThat(actual.getStatus()).isEqualTo(200);
        assertThat(actual.getContentType()).isEqualTo("application/json");
        assertThat(actual.getContentAsString()).isEqualTo("{\"message\":\"Hello, World!\"}");
    }

}
