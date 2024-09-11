package com.aimtech.arrowcore.domain.business.dto.requests.management;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryUpdateRequest {

    private String countryName;

    @Size(min = 3, max = 3, message = "{arrowcore.messages.errors.validation.Size}")
    private String isoCode;

    private Boolean isActive;
}
