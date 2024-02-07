package org.bananalaba.springcdtemplate.security.spring;

import static org.apache.commons.lang3.Validate.notNull;

import java.util.function.Supplier;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;

public class AccessControlExpressionRoot extends SecurityExpressionRoot {

    private final MethodInvocation invocation;

    public AccessControlExpressionRoot(Supplier<Authentication> authentication, MethodInvocation invocation) {
        super(authentication);
        this.invocation = notNull(invocation, "invocation required");
    }

    public void ownsOrIsCreatingNew(final String key) {
        throw new UnsupportedOperationException();
    }

    public void owns(final String key) {
        throw new UnsupportedOperationException();
    }

}
