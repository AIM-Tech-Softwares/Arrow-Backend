package com.aimtech.arrowcore.domain.business.dto.requests.admin;

import com.aimtech.arrowcore.core.annotation.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeUserPasswordRequest {

    @Schema(
            description = "Old password for the user",
            example = "OldP@ssw0rd!",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotEmpty(message = "{arrowcore.messages.errors.validation.NotEmpty}")
    @ValidPassword(message = "{arrowcore.errors.validation.custom.ValidPassword}")
    private String oldPassword;

    @Schema(
            description = "New password for the user",
            example = "NewP@ssw0rd!",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotEmpty(message = "{arrowcore.messages.errors.validation.NotEmpty}")
    @ValidPassword(message = "{arrowcore.errors.validation.custom.ValidPassword}")
    private String newPassword;
}
