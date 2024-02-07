package org.bananalaba.springcdtemplate.security.spring;

import java.util.function.Supplier;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInvocation;
import org.bananalaba.springcdtemplate.security.AclManager;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.core.Authentication;

@RequiredArgsConstructor
public class AccessControlExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    @NonNull
    private final AclManager aclManager;

    @Override
    public EvaluationContext createEvaluationContext(Supplier<Authentication> authentication, MethodInvocation invocation) {
        var root = new AccessControlExpressionRoot(authentication, invocation, aclManager);

        var context = (StandardEvaluationContext) super.createEvaluationContext(authentication, invocation);
        context.setRootObject(root);

        return context;
    }

}
