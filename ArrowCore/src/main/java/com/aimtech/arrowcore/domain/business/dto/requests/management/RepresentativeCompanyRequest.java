package com.aimtech.arrowcore.domain.business.dto.requests.management;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "ID of the representative", example = "1", requiredMode = Schema.RequiredMode.AUTO)
    @NotNull(message = "{arrowcore.messages.errors.validation.NotNull}")
    private Long representativeId;
}
