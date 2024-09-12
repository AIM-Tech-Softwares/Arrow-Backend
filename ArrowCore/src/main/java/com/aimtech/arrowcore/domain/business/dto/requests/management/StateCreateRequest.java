package com.aimtech.arrowcore.domain.business.dto.requests.management;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(
            description = "Name of the state",
            example = "São Paulo",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String stateName;

    @Schema(
            description = "Code of the state",
            example = "SP",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    @Size(min = 2, max = 2, message = "{arrowcore.messages.errors.validation.Size}")
    private String stateCode;

    @Schema(
            description = "Country ID associated with the state",
            example = "1",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotNull(message = "{arrowcore.messages.errors.validation.NotNull}")
    @Positive(message = "{arrowcore.messages.errors.validation.Positive}")
    private Long countryId;

    @Schema(
            description = "Indicates if the state is active",
            example = "true",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotNull(message = "{arrowcore.messages.errors.validation.NotNull}")
    private Boolean isActive;
}
