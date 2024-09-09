package com.aimtech.arrowcore.core.annotation;

import com.aimtech.arrowcore.core.annotation.validator.CNPJValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CNPJValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCNPJ {

    String message() default "Invalid CNPJ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
