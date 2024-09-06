package com.aimtech.arrowcore.core.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public @interface CheckSecurity {

    @interface User {
        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).USER_READ.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanRead {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).USER_CRETE.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanCreate {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).USER_UPDATE.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanUpdate {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).USER_CHANGE_STATUS.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanChangeStatus {}
    }
}
