package com.aimtech.arrowcore.domain.business.dto.responses.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Schema(
        name = "ValidationErrorResponse",
        description = "Validation Error Response"
)
@Getter
public class ValidationErrorResponse extends CustomErrorResponse {

    @Schema(description = "List of field-specific validation errors")
    private final List<FieldMessageResponse> errors = new ArrayList<>();

    public ValidationErrorResponse(OffsetDateTime timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public void addError(String fieldName, String message) {
        errors.removeIf(x -> x.getFieldName().equals(fieldName));
        errors.add(new FieldMessageResponse(fieldName, message));
    }
}
