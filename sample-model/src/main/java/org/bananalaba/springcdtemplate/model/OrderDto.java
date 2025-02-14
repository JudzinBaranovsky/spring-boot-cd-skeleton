package org.bananalaba.springcdtemplate.model;

import java.util.List;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

@Getter
@RequiredArgsConstructor
@Jacksonized
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderDto {

    @With
    @EqualsAndHashCode.Include
    private final String id;
    @NonNull
    private final List<OrderLineItemDto> lineItems;

}
