package com.aimtech.arrowcore.core.annotation;

import com.aimtech.arrowcore.core.annotation.validator.EmailIsRequiredValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailIsRequiredValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmailIsRequired {
    String message() default "Email is required";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
