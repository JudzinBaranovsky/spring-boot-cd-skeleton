package org.bananalaba.springcdtemplate.client;

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
public class ZenQuoteDto {

    private final String q;
    private final String a;
    private final String i;
    private final int c;
    private final String h;

}
