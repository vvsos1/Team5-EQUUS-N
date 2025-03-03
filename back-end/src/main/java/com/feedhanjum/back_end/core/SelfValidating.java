package com.feedhanjum.back_end.core;

import jakarta.validation.*;

import java.util.Set;

public abstract class SelfValidating<T extends SelfValidating<T>> {
    private static final ValidatorFactory factory;
    private final Validator validator;

    static {
        factory = Validation.buildDefaultValidatorFactory();
    }

    public SelfValidating() {
        this.validator = factory.getValidator();
    }

    protected void validateSelf() {
        Set<ConstraintViolation<T>> violations = validator.validate((T) this);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
