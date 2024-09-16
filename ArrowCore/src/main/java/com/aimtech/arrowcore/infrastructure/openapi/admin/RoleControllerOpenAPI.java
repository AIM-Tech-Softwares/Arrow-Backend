package com.aimtech.arrowcore.infrastructure.openapi.admin;

import com.aimtech.arrowcore.core.config.OpenAPIConfig;
import com.aimtech.arrowcore.domain.business.dto.responses.admin.RoleSummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Tag(name = "Admin | Role")
@SecurityRequirement(name = OpenAPIConfig.SECURITY_SCHEME)
public interface RoleControllerOpenAPI {

    @GetMapping
    @Operation(
            summary = "Get all roles",
            description = "Endpoint to get all roles."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful query",
                    content = @Content(
                            schema = @Schema(implementation = RoleSummaryResponse.class)
                    )),
    })
    ResponseEntity<List<RoleSummaryResponse>> findAll();
}
