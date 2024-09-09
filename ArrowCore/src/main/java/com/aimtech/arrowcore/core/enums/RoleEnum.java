package com.aimtech.arrowcore.core.enums;

public enum RoleEnum {
    USER_READ("ROLE_USER_READ"),
    USER_CRETE("ROLE_USER_CREATE"),
    USER_UPDATE("ROLE_USER_UPDATE"),
    USER_CHANGE_STATUS("ROLE_USER_CHANGE_STATUS"),
    COMPANY_READ("ROLE_COMPANY_READ"),
    COMPANY_CREATE("ROLE_COMPANY_CREATE"),
    COMPANY_UPDATE("ROLE_COMPANY_UPDATE"),
    COMPANY_CHANGE_STATUS("ROLE_COMPANY_CHANGE_STATUS"),
    COMPANY_CHANGE_REPRESENTATIVES("ROLE_COMPANY_CHANGE_REPRESENTATIVES"),
    COMPANY_ASSOCIATE_AS_BRANCH("ROLE_COMPANY_ASSOCIATE_AS_BRANCH");


    private final String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
