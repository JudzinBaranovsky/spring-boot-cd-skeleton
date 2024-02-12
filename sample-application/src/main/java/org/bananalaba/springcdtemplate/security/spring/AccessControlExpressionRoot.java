package org.bananalaba.springcdtemplate.security.spring;

import static org.apache.commons.lang3.Validate.notNull;

import java.util.Optional;
import java.util.function.Supplier;

import org.aopalliance.intercept.MethodInvocation;
import org.bananalaba.springcdtemplate.security.AclManager;
import org.bananalaba.springcdtemplate.security.OwnershipSubjectType;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;

public class AccessControlExpressionRoot extends SecurityExpressionRoot {

    private final MethodInvocation invocation;
    private final AclManager aclManager;

    public AccessControlExpressionRoot(Supplier<Authentication> authentication, MethodInvocation invocation, AclManager aclManager) {
        super(authentication);
        this.invocation = notNull(invocation, "invocation required");
        this.aclManager = notNull(aclManager, "aclManager required");
    }

    public boolean canEditOrCreate(final String key) {
        var subjectType = getSubjectType();
        var principalId = getPrincipalId();

        return aclManager.canEditOrCreate(subjectType, key, principalId);
    }

    public boolean canEdit(final String key) {
        var subjectType = getSubjectType();
        var principalId = getPrincipalId();

        return aclManager.canEditOrCreate(subjectType, key, principalId);
    }

    private String getSubjectType() {
        var invocationTarget = invocation.getMethod().getDeclaringClass();
        return getSubjectType(invocationTarget);
    }

    private String getSubjectType(Class<?> invocationTarget) {
        var typeAnnotation = AnnotationUtils.findAnnotation(invocationTarget, OwnershipSubjectType.class);
        return Optional.ofNullable(typeAnnotation)
            .map(OwnershipSubjectType::value)
            .orElseThrow(() -> new IllegalArgumentException("invocation target " + invocationTarget + " doesn't have @OwnershipSubjectType"));
    }

    private String getPrincipalId() {
        return getAuthentication().getName();
    }

}
