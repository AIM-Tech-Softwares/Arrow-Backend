package com.aimtech.arrowcore.domain.business.dto.responses.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FieldMessageResponse {

    @Schema(description = "Name of the field", example = "email")
    private String fieldName;

    @Schema(description = "Error message related to the field", example = "Email address is invalid")
    private String message;

}
