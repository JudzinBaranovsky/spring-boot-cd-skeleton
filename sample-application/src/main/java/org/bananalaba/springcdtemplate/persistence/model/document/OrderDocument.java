package org.bananalaba.springcdtemplate.persistence.model.document;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class OrderDocument {

    @Id
    @With
    @EqualsAndHashCode.Include
    private final String id;
    private final long dateTimeMillisCreated;
    @NonNull
    private final Set<OrderLineItemDocument> lineItems;

}
