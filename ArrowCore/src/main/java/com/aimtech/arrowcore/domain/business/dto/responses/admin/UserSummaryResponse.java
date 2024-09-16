package com.aimtech.arrowcore.domain.business.dto.responses.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryResponse {

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

    @Schema(description = "Status indicating if the user is active", example = "true")
    private Boolean isActive;

    @Schema(description = "Status indicating if the user's password is expired", example = "false")
    private Boolean isPasswordExpired;

    @Schema(description = "Status indicating if the user's account is locked", example = "false")
    private Boolean isAccountLocked;
}
