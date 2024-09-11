package com.aimtech.arrowcore.domain.business.dto.requests.management;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryCreateRequest {

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String countryName;

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    @Size(min = 3, max = 3, message = "{arrowcore.messages.errors.validation.Size}")
    private String isoCode;

    @NotNull(message = "{arrowcore.messages.errors.validation.NotNull}")
    private Boolean isActive;
}
