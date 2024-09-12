package com.aimtech.arrowcore.domain.business.dto.responses.management;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRepresentativeDetailResponse {

    @Schema(description = "Internal identifier of the representative", example = "12345")
    private Long internalId;

    @Schema(description = "CPF of the representative", example = "12345678900")
    private String cpf;

    @Schema(description = "Name of the representative", example = "John Doe")
    private String name;

    @Schema(description = "Fixed phone number of the representative", example = "2123456789")
    private String phoneFixed;

    @Schema(description = "Mobile phone number of the representative", example = "21987654321")
    private String phoneMobile;

    @Schema(description = "Email of the representative", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Position or role of the representative within the company", example = "CEO")
    private String position;

    @Schema(description = "Timestamp when the representative details were created", example = "2023-10-01T12:34:56Z")
    private OffsetDateTime createdAt;

    @Schema(description = "Timestamp when the representative details were last updated", example = "2023-10-10T15:20:35Z")
    private OffsetDateTime updatedAt;
}
