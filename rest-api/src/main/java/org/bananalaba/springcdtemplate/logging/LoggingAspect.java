package org.bananalaba.springcdtemplate.logging;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Map<Class<?>, Logger> loggers = new ConcurrentHashMap<>();
    private ObjectMapper mapper = new ObjectMapper();

    @Pointcut("@within(org.bananalaba.springcdtemplate.logging.Loggable)")
    public void loggableTypesPointcut() {
    }

    @Around("loggableTypesPointcut()")
    public Object log(final ProceedingJoinPoint joinPoint) throws Throwable {
        var type = joinPoint.getTarget().getClass();
        var logger = loggers.computeIfAbsent(type, LoggerFactory::getLogger);

        var methodName = joinPoint.getSignature().getName();
        var methodArguments = map(joinPoint.getArgs());
        var logHeader = "%s(%s)".formatted(methodName, methodArguments);

        try {
            var result = joinPoint.proceed();
            logger.info("{} returned {}", logHeader, map(result));

            return result;
        } catch (Throwable e) {
            logger.error("{} failed", logHeader, e);
            throw e;
        }
    }

    private String map(Object... arguments) {
        return Stream.of(arguments)
            .map(argument -> {
                try {
                    return mapper.writeValueAsString(argument);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("failed to log argument", e);
                }
            })
            .collect(Collectors.joining(", "));
    }

}
