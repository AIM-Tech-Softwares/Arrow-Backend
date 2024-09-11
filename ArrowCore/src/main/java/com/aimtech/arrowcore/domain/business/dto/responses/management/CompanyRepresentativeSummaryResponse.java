package com.aimtech.arrowcore.domain.business.dto.responses.management;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRepresentativeSummaryResponse {

    private Long internalId;

    private String cpf;

    private String name;

    private String phoneMobile;

    private String email;

    private String position;
}
