package com.example.employeemanagementsys.Service.Validation;

import org.springframework.stereotype.Component;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

@Component
public class ObjectValidator<T> {
    private final Validator validator;

    public ObjectValidator(Validator validator) {
        this.validator = validator;
    }

    public void validate(T request) {
        Set<ConstraintViolation<T>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(violations.iterator().next().getMessage());
        }
    }
}

