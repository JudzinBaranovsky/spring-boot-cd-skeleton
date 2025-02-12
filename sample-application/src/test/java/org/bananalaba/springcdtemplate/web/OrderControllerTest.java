package org.bananalaba.springcdtemplate.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.bananalaba.springcdtemplate.model.OrderDto;
import org.bananalaba.springcdtemplate.model.OrderLineItemDto;
import org.bananalaba.springcdtemplate.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    private OrderService orderService;
    @InjectMocks
    private OrderController controller;

    @Test
    public void shouldCreateOrder() {
        var order = new OrderDto(null, List.of(
           new OrderLineItemDto("LG LCD", 1, new BigDecimal("1500"))
        ));

        var expected = 123L;
        when(orderService.create(order)).thenReturn(expected);

        var actual = controller.create(order);
        assertThat(actual.getId()).isEqualTo(expected);
    }

}
