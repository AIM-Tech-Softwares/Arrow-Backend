package com.aimtech.arrowcore.core.interceptors;

import com.aimtech.arrowcore.core.utils.AuthUtils;
import com.aimtech.arrowcore.infrastructure.context.TenantContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Component
public class TenantInterceptor implements HandlerInterceptor {


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
            return Optional.ofNullable(request.getHeader("tenant"))
                    .map(String::toUpperCase).orElse("public");
        }
    }
}
