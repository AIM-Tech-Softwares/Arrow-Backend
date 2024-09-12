package com.aimtech.arrowcore.domain.business.dto.responses.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "CustomErrorResponse",
        description = "Custom Error Response"
)
public class CustomErrorResponse {
    @Schema(description = "Timestamp when the error occurred", example = "2023-10-01T12:34:56Z")
    private OffsetDateTime timestamp;

    @Schema(description = "HTTP status code returned by the server", example = "404")
    private Integer httpStatus;

    @Schema(description = "HTTP error message", example = "Not Found")
    private String httpError;

    @Schema(description = "Detailed error message", example = "Resource not found")
    private String error;

    @Schema(description = "The path of the request that resulted in the error", example = "/api/resource/1")
    private String path;
}
