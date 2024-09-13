package com.aimtech.arrowcore.core.annotation.validator;

import com.aimtech.arrowcore.core.annotation.ValidEmailIsRequired;
import com.aimtech.arrowcore.domain.business.usecases.admin.systemparameter_module.GetSystemParameterService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailIsRequiredValidator implements ConstraintValidator<ValidEmailIsRequired, String> {
    private final GetSystemParameterService getSystemParameterService;

    private static final String IS_EMAIL_REQUIRED_KEY = "admin.user.use_internal_domain";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Boolean isEmailRequired = getSystemParameterService.getBooleanValue(IS_EMAIL_REQUIRED_KEY);
        if (isEmailRequired){
            return value != null && !value.isEmpty();
        }
        return true;
    }
}
