package com.aimtech.arrowcore.core.enums;

public enum RoleEnum {
    USER_READ("ROLE_USER_READ"),
    USER_CRATE("ROLE_USER_CRATE"),
    USER_UPDATE("ROLE_USER_"),
    USER_CHANGE_STATUS("ROLE_USER_");


    private final String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
