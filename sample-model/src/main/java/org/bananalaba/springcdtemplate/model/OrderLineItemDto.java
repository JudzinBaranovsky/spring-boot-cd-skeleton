package org.bananalaba.springcdtemplate.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Getter
@RequiredArgsConstructor
@Jacksonized
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderLineItemDto {

    private final String productName;
    private final int quantity;
    private final BigDecimal priceUsd;

}
