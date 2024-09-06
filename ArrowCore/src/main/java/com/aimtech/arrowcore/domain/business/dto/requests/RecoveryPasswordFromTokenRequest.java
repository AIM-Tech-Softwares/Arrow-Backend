package com.aimtech.arrowcore.domain.business.dto.requests;

import com.aimtech.arrowcore.core.annotation.ValidPassword;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryPasswordFromTokenRequest {

        @NotBlank(message = "Field is required.")
        @ValidPassword
        private String newPassword;
}
