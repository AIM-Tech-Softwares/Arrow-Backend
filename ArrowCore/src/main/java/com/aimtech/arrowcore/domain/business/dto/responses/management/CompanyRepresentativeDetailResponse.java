package com.aimtech.arrowcore.domain.business.dto.responses.management;

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

    private Long internalId;

    private String cpf;

    private String name;

    private String phoneFixed;

    private String phoneMobile;

    private String email;

    private String position;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}
