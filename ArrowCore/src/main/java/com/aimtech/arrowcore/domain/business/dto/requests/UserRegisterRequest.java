package com.aimtech.arrowcore.domain.business.dto.requests;

import jakarta.persistence.Column;
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
public class UserRegisterRequest {

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String firstName;

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String lastName;

    @Email(message = "{arrowcore.messages.errors.validation.Email}")
    private String username;

    private Boolean isActive;

    private List<Long> profileIds;
}
