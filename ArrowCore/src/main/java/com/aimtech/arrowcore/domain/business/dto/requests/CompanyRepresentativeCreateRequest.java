package com.aimtech.arrowcore.domain.business.dto.requests;

import com.aimtech.arrowcore.core.annotation.ValidCPF;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    @ValidCPF(message = "{arrowcore.messages.errors.validation.ValidCPF}")
    private String cpf;

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String name;

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String phoneFixed;

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String phoneMobile;

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String email;

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String position;

    private List<Long> companiesInternalIds;
}
