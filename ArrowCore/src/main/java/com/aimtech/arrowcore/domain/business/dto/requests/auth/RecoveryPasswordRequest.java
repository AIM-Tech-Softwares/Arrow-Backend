package com.aimtech.arrowcore.domain.business.dto.requests.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryPasswordRequest {

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    @Email(message = "{arrowcore.messages.errors.validation.Email}")
    private String username;
}
