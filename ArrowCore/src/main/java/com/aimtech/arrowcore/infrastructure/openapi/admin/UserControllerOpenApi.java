package com.aimtech.arrowcore.infrastructure.openapi.admin;

import com.aimtech.arrowcore.core.config.OpenAPIConfig;
import com.aimtech.arrowcore.domain.business.dto.requests.admin.UserRegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Admin | User")
@SecurityRequirement(name = OpenAPIConfig.SECURITY_SCHEME)
public interface UserControllerOpenApi {

    @PostMapping
    @Operation(
            summary = "Register a new user",
            description = "Endpoint to register a new user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
    })
    ResponseEntity<Void> registerUser(
            @Valid @RequestBody UserRegisterRequest request
    );
}
