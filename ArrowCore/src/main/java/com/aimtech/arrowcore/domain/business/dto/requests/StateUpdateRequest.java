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
public class StateUpdateRequest {

    private String stateName;

    @Size(min = 2, max = 2, message = "{arrowcore.messages.errors.validation.Size}")
    private String stateCode;

    @Positive(message = "{arrowcore.messages.errors.validation.Positive}")
    private Long countryId;
}
