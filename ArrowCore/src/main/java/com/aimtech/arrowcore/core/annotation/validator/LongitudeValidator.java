package com.aimtech.arrowcore.core.annotation.validator;

import com.aimtech.arrowcore.core.annotation.ValidLongitude;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class LongitudeValidator implements ConstraintValidator<ValidLongitude, BigDecimal> {

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.compareTo(BigDecimal.valueOf(-180)) >= 0 && value.compareTo(BigDecimal.valueOf(180)) <= 0;
    }
}
