package com.aimtech.arrowcore.domain.business.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreetTypeCreateRequest {

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String streetTypeName;

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String streetTypeAbbreviation;

    @NotNull(message = "{arrowcore.messages.errors.validation.NotNull}")
    private Boolean isActive;
}
