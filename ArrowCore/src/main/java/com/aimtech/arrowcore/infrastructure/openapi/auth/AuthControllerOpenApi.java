package com.aimtech.arrowcore.infrastructure.openapi.auth;

import com.aimtech.arrowcore.domain.business.dto.requests.auth.LoginWithUsernameAndPasswordRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.auth.RecoveryPasswordFromTokenRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.auth.RecoveryPasswordRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.auth.LoginWithUsernameAndPasswordResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Auth")
public interface AuthControllerOpenApi {

    @PostMapping("/login")
    @Operation(
            summary = "Login with username and password",
            description = "Endpoint to login using username and password."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login",
                    content = @Content(
                            schema = @Schema(implementation = LoginWithUsernameAndPasswordResponse.class)
                    )),
    })
    ResponseEntity<LoginWithUsernameAndPasswordResponse> loginWithUsernameAndPassword(
            @RequestBody(description = "Login data", required = true,
                    content = @Content(
                            schema = @Schema(implementation = LoginWithUsernameAndPasswordRequest.class)
                    ))
            LoginWithUsernameAndPasswordRequest request
    );


    @PostMapping("/recovery-password")
    @Operation(
            summary = "Request password recovery",
            description = "Endpoint to request a password recovery."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recovery request successful"),
    })
    ResponseEntity<Void> recoveryPassword(
            @RequestBody(description = "Recovery password data", required = true,
                    content = @Content(
                            schema = @Schema(implementation = RecoveryPasswordRequest.class)
                    ))
            RecoveryPasswordRequest request
    );


    @PostMapping("/recovery-password/{tenant}/{token}")
    @Operation(
            summary = "Set new password",
            description = "Endpoint to set a new password using recovery token."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New password set successfully"),
    })
    ResponseEntity<Void> setNewPassword(
            @RequestBody(description = "New password data", required = true,
                    content = @Content(
                            schema = @Schema(implementation = RecoveryPasswordFromTokenRequest.class)
                    ))
            RecoveryPasswordFromTokenRequest request,
            @PathVariable String tenant,
            @PathVariable String token
    );
}