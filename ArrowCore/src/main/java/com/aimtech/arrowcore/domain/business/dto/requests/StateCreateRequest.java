package com.aimtech.arrowcore.domain.business.dto.requests;

import com.aimtech.arrowcore.domain.entities.Country;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StateCreateRequest {

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String stateName;

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    @Size(min = 2, max = 2, message = "{arrowcore.messages.errors.validation.Size}")
    private String stateCode;

    @NotNull(message = "{arrowcore.messages.errors.validation.NotNull}")
    @Positive(message = "{arrowcore.messages.errors.validation.Positive}")
    private Long countryId;
}
