package org.bananalaba.springcdtemplate.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bananalaba.springcdtemplate.local.LocalRunnerConfig;
import org.bananalaba.springcdtemplate.model.OrderDto;
import org.bananalaba.springcdtemplate.model.OrderLineItemDto;
import org.bananalaba.springcdtemplate.model.OrderReferenceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@Import(LocalRunnerConfig.class)
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper jsonMapper;

    @Test
    public void shouldSaveOrder() throws Exception {
        var order = new OrderDto(
            null,
            List.of(
                new OrderLineItemDto("foo", 1, BigDecimal.ONE)
            )
        );
        var orderJson = toJson(order);

        var responseOnCreate =
            mvc.perform(post("/api/v1/orders")
                    .contentType("application/json")
                    .accept("application/json")
                    .content(orderJson)
                )
                .andReturn()
                .getResponse();
        assertThat(responseOnCreate.getStatus()).isEqualTo(201);

        var orderReference = fromJson(responseOnCreate.getContentAsString(), OrderReferenceDto.class);
        var responseOnGet = mvc.perform(get("/api/v1/orders/" + orderReference.getId()))
            .andReturn()
            .getResponse();

        assertThat(responseOnGet.getStatus()).isEqualTo(200);
        var actual = fromJson(responseOnGet.getContentAsString(), OrderDto.class);
        assertThat(actual.getId()).isEqualTo(orderReference.getId());
        assertThat(actual.getLineItems()).usingRecursiveFieldByFieldElementComparator().isEqualTo(order.getLineItems());
    }

    private String toJson(Object object) {
        try {
            return jsonMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T fromJson(String json, Class<T> type) {
        try {
            return jsonMapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
