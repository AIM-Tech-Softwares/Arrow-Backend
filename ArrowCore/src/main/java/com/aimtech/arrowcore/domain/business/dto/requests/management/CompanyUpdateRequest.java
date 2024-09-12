package com.aimtech.arrowcore.domain.business.dto.requests.management;

import com.aimtech.arrowcore.core.annotation.ValidCNPJ;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUpdateRequest {

    @Schema(
            description = "Trade name of the company",
            example = "Tech Co.",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String tradeName;

    @Schema(
            description = "Corporate name of the company",
            example = "Tech Co. Ltd.",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String corporateName;

    @Schema(
            description = "State registration of the company",
            example = "123456789",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String stateRegistration;

    @Schema(
            description = "Municipal registration of the company",
            example = "11223344",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String municipalRegistration;

    @Schema(
            description = "Foundation date of the company",
            example = "2000-01-01",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @PastOrPresent(message = "{arrowcore.messages.errors.validation.PastOrPresent}")
    private Date foundationDate;

    @Schema(
            description = "Tax regime ID of the company",
            example = "1",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotNull(message = "{arrowcore.messages.errors.validation.NotNull}")
    private Long taxRegimeId;

    @Schema(
            description = "Email address of the company",
            example = "contact@techco.com",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    @Email(message = "{arrowcore.messages.errors.validation.Email}")
    private String email;

    @Schema(
            description = "Phone number of the company",
            example = "1112345678",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String phone;

    @Schema(
            description = "Street type ID where the company is located",
            example = "1",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotNull(message = "{arrowcore.messages.errors.validation.NotNull}")
    private Long streetTypeId;

    @Schema(
            description = "Street name where the company is located",
            example = "Main St.",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String streetName;

    @Schema(
            description = "Street number where the company is located",
            example = "123",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String streetNumber;

    @Schema(
            description = "Neighborhood where the company is located",
            example = "Downtown",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String neighborhood;

    @Schema(
            description = "Complement for the company's address",
            example = "Suite 100"
    )
    private String complement;

    @Schema(
            description = "ZIP code of the company's address",
            example = "12345678",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    @Size(min = 8, max = 8, message = "{arrowcore.messages.errors.validation.Size}")
    private String zipCode;

    @Schema(
            description = "City ID where the company is located",
            example = "1",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotNull(message = "{arrowcore.messages.errors.validation.NotNull}")
    private Long cityId;

    @Schema(
            description = "URL of the company's logo",
            example = "http://www.techco.com/logo.png"
    )
    private String logoUrl;

    @Schema(
            description = "Indicates if the company is active",
            example = "true"
    )
    private Boolean isActive;

    @Schema(
            description = "ID of the parent company",
            example = "1"
    )
    private Long parentCompanyId;
}
