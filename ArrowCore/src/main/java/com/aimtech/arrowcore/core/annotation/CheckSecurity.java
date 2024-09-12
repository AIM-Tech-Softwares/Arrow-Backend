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

    @interface Company{
        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).COMPANY_READ.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanRead {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).COMPANY_CREATE.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanCreate {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).COMPANY_UPDATE.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanUpdate {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).COMPANY_CHANGE_STATUS.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanChangeStatus {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).COMPANY_CHANGE_REPRESENTATIVES.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanChangeRepresentatives {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).COMPANY_ASSOCIATE_AS_BRANCH.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CamAssociateAsBranch {}
    }

    @interface Country{
        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).COUNTRY_READ.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanRead {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).COUNTRY_CREATE.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanCreate {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).COUNTRY_UPDATE.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanUpdate {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).COUNTRY_CHANGE_STATUS.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanChangeStatus {}
    }

    @interface State{
        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).STATE_READ.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanRead {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).STATE_CREATE.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanCreate {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).STATE_UPDATE.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanUpdate {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).STATE_CHANGE_STATUS.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanChangeStatus {}
    }

    @interface City{
        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).CITY_READ.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanRead {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).CITY_CREATE.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanCreate {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).CITY_UPDATE.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanUpdate {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).CITY_CHANGE_STATUS.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanChangeStatus {}
    }

    @interface StreetType{
        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).STREET_TYPE_READ.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanRead {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).STREET_TYPE_CREATE.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanCreate {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).STREET_TYPE_UPDATE.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanUpdate {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).STREET_TYPE_CHANGE_STATUS.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanChangeStatus {}
    }
}
