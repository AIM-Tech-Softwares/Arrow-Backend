package com.aimtech.arrowcore.domain.business.dto.responses.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterResponse {

    @Schema(description = "First name of the user", example = "John")
    private String firstName;

    @Schema(description = "External ID of the user", example = "EXT-USER-001")
    private String externalId;
}
