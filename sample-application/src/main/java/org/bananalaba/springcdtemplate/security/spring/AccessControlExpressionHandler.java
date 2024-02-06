package org.bananalaba.springcdtemplate.security.spring;

import java.util.function.Supplier;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.core.Authentication;

public class AccessControlExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    @Override
    public EvaluationContext createEvaluationContext(Supplier<Authentication> authentication, MethodInvocation invocation) {
        var root = new AccessControlExpressionRoot(authentication, invocation);

        var context = (StandardEvaluationContext) super.createEvaluationContext(authentication, invocation);
        context.setRootObject(root);

        return context;
    }

}
