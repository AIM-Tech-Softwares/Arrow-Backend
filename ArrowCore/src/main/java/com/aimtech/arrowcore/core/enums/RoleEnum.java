package com.aimtech.arrowcore.core.enums;

public enum RoleEnum {
    USER_READ("ROLE_USER_READ"),
    USER_CRETE("ROLE_USER_CREATE"),
    USER_UPDATE("ROLE_USER_UPDATE"),
    USER_CHANGE_STATUS("ROLE_USER_CHANGE_STATUS");


    private final String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
