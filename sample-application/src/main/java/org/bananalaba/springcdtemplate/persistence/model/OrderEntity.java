package org.bananalaba.springcdtemplate.persistence.model;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Table("order")
public class OrderEntity {

    @Id
    @With
    @EqualsAndHashCode.Include
    private final Long id;
    private final long dateTimeMillisCreated;
    @NonNull
    private final Set<OrderLineItemEntity> lineItems;


}
