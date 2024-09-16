package com.aimtech.arrowcore.core.annotation.validator;

import com.aimtech.arrowcore.core.annotation.ValidUsername;
import com.aimtech.arrowcore.domain.business.usecases.admin.systemparameter_module.GetSystemParameterService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {
    private final GetSystemParameterService getSystemParameterService;

    private static final String ADMIN_USER_USE_INTERNAL_DOMAIN = "admin.user.use_internal_domain";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Boolean useInternalDomain = getSystemParameterService.getBooleanValue(ADMIN_USER_USE_INTERNAL_DOMAIN);
        if (useInternalDomain){
            return !value.contains("@");
        } else {
            return EMAIL_PATTERN.matcher(value).matches();
        }
    }
}
