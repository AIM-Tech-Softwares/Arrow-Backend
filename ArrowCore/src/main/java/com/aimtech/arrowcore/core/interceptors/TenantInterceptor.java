package com.aimtech.arrowcore.core.interceptors;

import com.aimtech.arrowcore.core.utils.AuthUtils;
import com.aimtech.arrowcore.infrastructure.context.TenantContext;
import com.aimtech.arrowcore.infrastructure.exceptions.InvalidPasswordRecoverTokenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class TenantInterceptor implements HandlerInterceptor {
    private final MessageSource messageSource;

    private static final Pattern URL_PATTERN_RECOVERY_PASSWORD_TOKEN = Pattern.compile("/auth/recovery-password/([^/]+)/([^/]+)");

    @Override
    public boolean preHandle(@NonNull HttpServletRequest  request, HttpServletResponse response, Object handler) {
        TenantContext.setCurrentTenant(getUserTenant(request));
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView  modelAndView
    ) throws Exception{
        TenantContext.clear();
    }

    private String getUserTenant(HttpServletRequest request) {
        if (AuthUtils.getUserTenant() != null) {
            return AuthUtils.getUserTenant();
        } else {
            String requestURI = request.getRequestURI();
            if (isValidRecoveryPasswordUrl(requestURI)) {
                return extractTenantFromUrl(requestURI);
            } else {
                return Optional.ofNullable(request.getHeader("tenant"))
                        .map(String::toUpperCase).orElse("public");
            }
        }
    }

    private boolean isValidRecoveryPasswordUrl(String requestURI) {
        Matcher matcher = URL_PATTERN_RECOVERY_PASSWORD_TOKEN.matcher(requestURI);
        return matcher.matches();
    }

    private String extractTenantFromUrl(String requestURI) {
        Matcher matcher = URL_PATTERN_RECOVERY_PASSWORD_TOKEN.matcher(requestURI);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new InvalidPasswordRecoverTokenException(
                messageSource.getMessage(
                        "arrowcore.exceptions.InvalidPasswordRecoverTokenException",
                        null,
                        LocaleContextHolder.getLocale()
                )
        );
    }
}
