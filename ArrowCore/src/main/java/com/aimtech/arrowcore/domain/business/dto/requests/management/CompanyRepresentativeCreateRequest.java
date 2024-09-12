package com.aimtech.arrowcore.domain.business.dto.requests.management;

import com.aimtech.arrowcore.core.annotation.ValidCPF;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRepresentativeCreateRequest {

    @Schema(
            description = "CPF of the representative",
            example = "12345678909",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    @ValidCPF(message = "{arrowcore.errors.validation.custom.ValidCPF}")
    private String cpf;

    @Schema(
            description = "Name of the representative",
            example = "John Doe",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String name;

    @Schema(
            description = "Fixed phone number of the representative",
            example = "1123456789",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String phoneFixed;

    @Schema(
            description = "Mobile phone number of the representative",
            example = "11912345678",
            requiredMode = Schema.RequiredMode.AUTO)
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String phoneMobile;

    @Schema(
            description = "Email address of the representative",
            example = "john.doe@example.com",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    @Email(message = "{arrowcore.messages.errors.validation.Email}")
    private String email;

    @Schema(
            description = "Position of the representative in the company",
            example = "CEO",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String position;

    @Schema(
            description = "List of internal IDs for companies the representative is associated with",
            example = "[1, 2, 3]"
    )
    private List<Long> companiesInternalIds;
}
