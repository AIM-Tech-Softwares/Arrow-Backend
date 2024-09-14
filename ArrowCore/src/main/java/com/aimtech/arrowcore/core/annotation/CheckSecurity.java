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

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).USER_READ_EXT.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanReadExt {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).USER_CREATE.roleName) and isAuthenticated()")
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

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).USER_CHANGE_EXT_PW.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanChangeExtPassword {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).USER_UNLOCK_EXT_ACC.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanUnlockExtAccount {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).USER_CHANGE_ME_PW.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanChangeMePassword {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).USER_UPDATE_ME.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanUpdateMe {}
    }

    @interface Profile{
        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).PROFILE_READ.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanRead {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).PROFILE_CREATE.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanCreate {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).PROFILE_UPDATE.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanUpdate {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).PROFILE_CHANGE_STATUS.roleName) and isAuthenticated()")
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

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).COMPANY_CHANGE_REPS.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanChangeRepresentatives {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).COMPANY_ASSOC_BRANCH.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CamAssociateAsBranch {}
    }

    @interface Role {
        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).ROLE_LIST_ALL.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanRead {}
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
        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).ST_TYPE_READ.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanRead {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).ST_TYPE_CREATE.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanCreate {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).ST_TYPE_UPDATE.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanUpdate {}

        @PreAuthorize("hasAuthority(T(com.aimtech.arrowcore.core.enums.RoleEnum).ST_TYPE_CHANGE_STATUS.roleName) and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface CanChangeStatus {}
    }
}
