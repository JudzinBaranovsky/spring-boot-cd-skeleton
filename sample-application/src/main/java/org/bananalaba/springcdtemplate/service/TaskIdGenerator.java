package org.bananalaba.springcdtemplate.service;

import java.util.function.Supplier;

@FunctionalInterface
public interface TaskIdGenerator extends Supplier<String> {
}
