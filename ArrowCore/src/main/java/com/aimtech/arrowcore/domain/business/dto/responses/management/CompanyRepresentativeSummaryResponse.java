package com.aimtech.arrowcore.domain.business.dto.responses.management;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRepresentativeSummaryResponse {

    @Schema(description = "Internal identifier of the representative", example = "12345")
    private Long internalId;

    @Schema(description = "CPF of the representative", example = "12345678900")
    private String cpf;

    @Schema(description = "Name of the representative", example = "John Doe")
    private String name;

    @Schema(description = "Mobile phone number of the representative", example = "21987654321")
    private String phoneMobile;

    @Schema(description = "Email of the representative", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Position or role of the representative within the company", example = "CEO")
    private String position;
}
