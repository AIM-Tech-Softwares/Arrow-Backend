package com.aimtech.arrowcore.domain.business.dto.requests.auth;

import com.aimtech.arrowcore.core.annotation.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class LoginWithUsernameAndPasswordRequest {

    @Schema(
            description = "Username address of the user",
            example = "user@example.com",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    @Email(message = "{arrowcore.messages.errors.validation.Email}")
    private String username;

    @Schema(
            description = "Password of the user",
            example = "P@ssw0rd!",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    @ValidPassword(message = "{arrowcore.errors.validation.custom.ValidPassword}")
    private String password;
}
