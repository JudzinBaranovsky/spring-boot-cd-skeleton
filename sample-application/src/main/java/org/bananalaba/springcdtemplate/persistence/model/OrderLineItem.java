package org.bananalaba.springcdtemplate.persistence.model;

import static org.apache.commons.lang3.Validate.isTrue;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table("line_item")
public class OrderLineItem {

    @Id
    @EqualsAndHashCode.Include
    private final Long id;
    private final String productName;
    private final int quantity;
    private final BigDecimal priceUsd;

    public OrderLineItem(final String productName, final int quantity, final BigDecimal priceUsd) {
        this(null, productName, quantity, priceUsd);
    }

    @Builder
    @PersistenceCreator
    public OrderLineItem(final Long id, final @NonNull String productName, final int quantity, final @NonNull BigDecimal priceUsd) {
        this.id = id;
        this.productName = productName;

        isTrue(quantity > 0, "quantity must be > 0");
        this.quantity = quantity;

        isTrue(priceUsd.compareTo(BigDecimal.ZERO) > 0, "priceUsd must be > 0");
        this.priceUsd = priceUsd;
    }

}
