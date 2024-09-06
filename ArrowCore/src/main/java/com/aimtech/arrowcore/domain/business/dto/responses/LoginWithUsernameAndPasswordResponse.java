package com.aimtech.arrowcore.domain.business.dto.responses;

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
    private String accessToken;
    private OffsetDateTime generatedAt;
    private OffsetDateTime expiresAt;
}
