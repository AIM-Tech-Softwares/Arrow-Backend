package com.aimtech.arrowcore.domain.business.dto.requests;

import com.aimtech.arrowcore.core.annotation.ValidPassword;
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

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    @Email(message = "{arrowcore.messages.errors.validation.Email}")
    private String username;

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    @ValidPassword(message = "{arrowcore.errors.validation.custom.ValidPassword}")
    private String password;
}
