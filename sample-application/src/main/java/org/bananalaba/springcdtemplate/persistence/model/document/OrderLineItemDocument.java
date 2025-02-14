package org.bananalaba.springcdtemplate.persistence.model.document;

import static org.apache.commons.lang3.Validate.isTrue;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class OrderLineItemDocument {

    private final String productName;
    private final int quantity;
    private final BigDecimal priceUsd;

    public OrderLineItemDocument(final @NonNull String productName, final int quantity, final @NonNull BigDecimal priceUsd) {
        this.productName = productName;

        isTrue(quantity > 0, "quantity must be > 0");
        this.quantity = quantity;

        isTrue(priceUsd.compareTo(BigDecimal.ZERO) > 0, "priceUsd must be > 0");
        this.priceUsd = priceUsd;
    }

}
