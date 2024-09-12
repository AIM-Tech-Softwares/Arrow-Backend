package com.aimtech.arrowcore.domain.business.dto.requests.auth;

import com.aimtech.arrowcore.core.annotation.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryPasswordFromTokenRequest {

        @Schema(
                description = "New password for the user",
                example = "NewP@ssw0rd!",
                requiredMode = Schema.RequiredMode.AUTO
        )
        @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
        @ValidPassword(message = "{arrowcore.errors.validation.custom.ValidPassword}")
        private String newPassword;
}
