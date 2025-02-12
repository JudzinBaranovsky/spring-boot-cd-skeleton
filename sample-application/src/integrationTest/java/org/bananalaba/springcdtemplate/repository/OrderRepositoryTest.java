package org.bananalaba.springcdtemplate.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Set;

import org.bananalaba.springcdtemplate.local.LocalRunnerConfig;
import org.bananalaba.springcdtemplate.persistence.model.Order;
import org.bananalaba.springcdtemplate.persistence.model.OrderLineItem;
import org.bananalaba.springcdtemplate.persistence.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(properties = "node.ip=127.0.0.1")
@Import(LocalRunnerConfig.class)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository repository;

    @Test
    public void shouldSaveOrder() {
        var order = new Order(
            null,
            System.currentTimeMillis(),
            Set.of(
                new OrderLineItem("foo", 1, BigDecimal.ONE)
            )
        );

        var saved = repository.save(order);
        assertThat(saved.getId()).isNotNull();
    }

}
