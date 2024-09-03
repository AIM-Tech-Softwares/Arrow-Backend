package com.aimtech.arrowcore.domain.business.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterResponse {

    private String firstName;
    private String externalId;
    private Long internalId;
}
