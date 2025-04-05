package org.bananalaba.springcdtemplate.resilience;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.function.Supplier;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ResilientAspect {

    @NonNull
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;
    @NonNull
    private final Environment environment;
    @NonNull
    private final ConversionService conversionService;

    @Around("@annotation(org.bananalaba.springcdtemplate.resilience.Resilient)")
    public Object wrapCall(final ProceedingJoinPoint joinPoint) {
        var signature = (MethodSignature) joinPoint.getSignature();
        var annotation = signature.getMethod().getAnnotation(Resilient.class);

        var circuitBreakerName = annotation.value();
        if (circuitBreakerName.isEmpty()) {
            circuitBreakerName = "default";
        }

        var circuitBreaker = circuitBreakerFactory.create(circuitBreakerName);
        Supplier<Object> joinPointCall = () -> rethrowIfError(joinPoint::proceed);

        if (isEmpty(annotation.fallback())) {
            return circuitBreaker.run(joinPointCall);
        }

        var fallbackRawValue = environment.resolveRequiredPlaceholders(annotation.fallback());
        var fallbackValue = conversionService.convert(fallbackRawValue, signature.getReturnType());
        return circuitBreaker.run(joinPointCall, error -> fallbackValue);
    }

    private Object rethrowIfError(final Call call) {
        try {
            return call.execute();
        } catch (Throwable e) {
            throw new UnhandledError("original call failed", e);
        }
    }

    @FunctionalInterface
    private interface Call {

        Object execute() throws Throwable;

    }

    private static class UnhandledError extends RuntimeException {

        UnhandledError(String message, Throwable cause) {
            super(message, cause);
        }

    }

}
