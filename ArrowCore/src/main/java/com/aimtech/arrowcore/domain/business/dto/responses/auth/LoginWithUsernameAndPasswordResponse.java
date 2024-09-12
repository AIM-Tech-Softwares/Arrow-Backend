package com.aimtech.arrowcore.domain.business.dto.responses.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginWithUsernameAndPasswordResponse {
    @Schema(description = "Access token received upon successful authentication", example = "eyJhbGciOiJIUzI1NiI...")
    private String accessToken;

    @Schema(description = "Timestamp indicating when the token was generated", example = "2023-10-01T12:34:56Z")
    private OffsetDateTime generatedAt;

    @Schema(description = "Timestamp indicating when the token will expire", example = "2023-10-02T12:34:56Z")
    private OffsetDateTime expiresAt;
}
