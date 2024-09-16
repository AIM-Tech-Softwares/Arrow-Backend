package com.aimtech.arrowcore.domain.business.dto.responses.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponse {

    @Schema(description = "External identifier of the user", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID externalId;

    @Schema(description = "First name of the user", example = "John")
    private String firstName;

    @Schema(description = "Last name of the user", example = "Doe")
    private String lastName;

    @Schema(description = "Username of the user", example = "johndoe@public.com.br")
    private String username;

    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Date of the last password change", example = "2023-05-12T14:12:45Z")
    private Date lastPasswordChangeDate;

    @Schema(description = "Date and time of the last failed login attempt", example = "2023-10-01T08:45:00Z")
    private Date lastFailedLoginTime;

    @Schema(description = "Date and time of the last successful login", example = "2023-10-10T10:00:00Z")
    private OffsetDateTime lastLogin;

    @Schema(description = "Status indicating if the user is active", example = "true")
    private Boolean isActive = true;

    @Schema(description = "Status indicating if this is the user's first login", example = "true")
    private Boolean isFirstLogin;

    @Schema(description = "Status indicating if the user's password is expired", example = "false")
    private Boolean isPasswordExpired;

    @Schema(description = "Status indicating if the user's account is expired", example = "false")
    private Boolean isAccountExpired;

    @Schema(description = "Status indicating if the user's account is locked", example = "false")
    private Boolean isAccountLocked;

    @Schema(description = "Number of failed login attempts", example = "3")
    private Integer failedLoginAttempts;

    @Schema(description = "Tenant domain associated with the user", example = "public.com.br")
    private String tenantDomain;

    @Schema(description = "List of profile names associated with the user", example = "[\"Admin\", \"User\"]")
    private List<String> profilesName;

    @Schema(description = "List of profile identifiers associated with the user", example = "[1, 2]")
    private List<Long> profilesId;
}
