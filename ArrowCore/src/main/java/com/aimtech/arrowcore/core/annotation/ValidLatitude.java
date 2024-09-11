package com.aimtech.arrowcore.core.annotation;

import com.aimtech.arrowcore.core.annotation.validator.LatitudeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LatitudeValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLatitude {
    String message() default "Invalid latitude";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
