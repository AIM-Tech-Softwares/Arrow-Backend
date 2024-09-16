package com.aimtech.arrowcore.domain.business.dto.requests.admin;

import com.aimtech.arrowcore.core.annotation.ValidEmailIsRequired;
import com.aimtech.arrowcore.core.annotation.ValidUsername;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    @Schema(
            description = "First name of the user",
            example = "John",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String firstName;

    @Schema(
            description = "Last name of the user",
            example = "Doe",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String lastName;

    @Email(message = "{arrowcore.messages.errors.validation.Email}")
    @ValidEmailIsRequired
    private String email;


    @Schema(description = "Status indicating whether the user is active", example = "true")
    private Boolean isActive;

    @Schema(description = "List of profile IDs associated with the user", example = "[1, 2, 3]")
    private List<Long> profileIds;
}
