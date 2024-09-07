package com.aimtech.arrowcore.domain.business.dto.requests;

import com.aimtech.arrowcore.core.annotation.ValidCNPJ;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRegisterRequest {

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String tradeName;

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String corporateName;

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    @ValidCNPJ(message = "{arrowcore.errors.validation.custom.ValidCNPJ}")
    private String cnpj;

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String stateRegistration;

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String municipalRegistration;

    @PastOrPresent(message = "{arrowcore.messages.errors.validation.PastOrPresent}")
    private Date foundationDate;

    @NotNull(message = "{arrowcore.messages.errors.validation.NotNull}")
    private Long taxRegimeId;

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    @Email(message = "{arrowcore.messages.errors.validation.Email}")
    private String email;

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String phone;

    @NotNull(message = "{arrowcore.messages.errors.validation.NotNull}")
    private Long streetTypeId;

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String streetName;

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String streetNumber;

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String neighborhood;

    private String complement;

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    @Size(min = 8, max = 8, message = "{arrowcore.messages.errors.validation.Size}")
    private String zipCode;

    @NotNull(message = "{arrowcore.messages.errors.validation.NotNull}")
    private Long cityId;

    private String logoUrl;

    private Boolean isActive;

    private Long parentCompanyId;
}
