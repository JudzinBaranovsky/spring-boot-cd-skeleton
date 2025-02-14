package org.bananalaba.springcdtemplate.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Getter
@RequiredArgsConstructor
@Jacksonized
@Builder
@EqualsAndHashCode
public class OrderReferenceDto {

    private final String id;

}
