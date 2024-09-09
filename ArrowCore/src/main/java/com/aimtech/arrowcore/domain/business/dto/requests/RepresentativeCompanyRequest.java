package com.aimtech.arrowcore.domain.business.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepresentativeCompanyRequest {

    @NotNull(message = "{arrowcore.messages.errors.validation.NotNull}")
    private Long representativeId;
}
