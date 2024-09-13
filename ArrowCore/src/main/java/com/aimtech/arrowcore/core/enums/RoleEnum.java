package com.aimtech.arrowcore.core.enums;

public enum RoleEnum {
    USER_READ("ROLE_U1"),
    USER_READ_EXT("ROLE_U2"),
    USER_CREATE("ROLE_U3"),
    USER_UPDATE("ROLE_U4"),
    USER_CHANGE_STATUS("ROLE_U5"),
    USER_CHANGE_EXT_PW("ROLE_U6"),
    USER_UNLOCK_EXT_ACC("ROLE_U7"),
    USER_CHANGE_ME_PW("ROLE_U8"),
    USER_UPDATE_ME("ROLE_U9"),
    COMPANY_READ("ROLE_C1"),
    COMPANY_CREATE("ROLE_C2"),
    COMPANY_UPDATE("ROLE_C3"),
    COMPANY_CHANGE_STATUS("ROLE_C4"),
    COMPANY_CHANGE_REPS("ROLE_C5"),
    COMPANY_ASSOC_BRANCH("ROLE_C6"),
    COUNTRY_READ("ROLE_CN1"),
    COUNTRY_CREATE("ROLE_CN2"),
    COUNTRY_UPDATE("ROLE_CN3"),
    COUNTRY_CHANGE_STATUS("ROLE_CN4"),
    STATE_READ("ROLE_S1"),
    STATE_CREATE("ROLE_S2"),
    STATE_UPDATE("ROLE_S3"),
    STATE_CHANGE_STATUS("ROLE_S4"),
    CITY_READ("ROLE_CT1"),
    CITY_CREATE("ROLE_CT2"),
    CITY_UPDATE("ROLE_CT3"),
    CITY_CHANGE_STATUS("ROLE_CT4"),
    ST_TYPE_READ("ROLE_ST1"),
    ST_TYPE_CREATE("ROLE_ST2"),
    ST_TYPE_UPDATE("ROLE_ST3"),
    ST_TYPE_CHANGE_STATUS("ROLE_ST4");

    private final String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
