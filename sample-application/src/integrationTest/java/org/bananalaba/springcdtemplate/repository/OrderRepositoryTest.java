package org.bananalaba.springcdtemplate.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Set;

import org.bananalaba.springcdtemplate.local.LocalRunnerConfig;
import org.bananalaba.springcdtemplate.persistence.model.OrderEntity;
import org.bananalaba.springcdtemplate.persistence.model.OrderLineItemEntity;
import org.bananalaba.springcdtemplate.persistence.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("postgresql")
@Import(LocalRunnerConfig.class)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository repository;

    @Test
    public void shouldSaveOrder() {
        var order = new OrderEntity(
            null,
            System.currentTimeMillis(),
            Set.of(
                new OrderLineItemEntity("foo", 1, BigDecimal.ONE)
            )
        );

        var saved = repository.save(order);
        assertThat(saved.getId()).isNotNull();
    }

}
