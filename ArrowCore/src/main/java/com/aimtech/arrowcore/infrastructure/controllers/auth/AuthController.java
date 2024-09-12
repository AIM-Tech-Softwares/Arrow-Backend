package com.aimtech.arrowcore.infrastructure.controllers.auth;

import com.aimtech.arrowcore.domain.business.dto.requests.auth.LoginWithUsernameAndPasswordRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.auth.RecoveryPasswordFromTokenRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.auth.RecoveryPasswordRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.auth.LoginWithUsernameAndPasswordResponse;
import com.aimtech.arrowcore.domain.business.usecases.auth.GeneratePasswordRecoveryTokenService;
import com.aimtech.arrowcore.domain.business.usecases.auth.LoginWithUsernameAndPasswordService;
import com.aimtech.arrowcore.domain.business.usecases.auth.RecoveryPasswordFromTokenService;
import com.aimtech.arrowcore.infrastructure.openapi.auth.AuthControllerOpenApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController implements AuthControllerOpenApi {
    private final LoginWithUsernameAndPasswordService loginWithUsernameAndPasswordService;
    private final GeneratePasswordRecoveryTokenService generatePasswordRecoveryTokenService;
    private final RecoveryPasswordFromTokenService recoveryPasswordFromTokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginWithUsernameAndPasswordResponse> loginWithUsernameAndPassword(
            @Valid @RequestBody LoginWithUsernameAndPasswordRequest request
    ) {
        LoginWithUsernameAndPasswordResponse result = this.loginWithUsernameAndPasswordService.execute(request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/recovery-password")
    public ResponseEntity<Void> recoveryPassword(@Valid @RequestBody RecoveryPasswordRequest request) {
        generatePasswordRecoveryTokenService.execute(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/recovery-password/{tenant}/{token}")
    public ResponseEntity<Void> setNewPassword(
            @Valid @RequestBody RecoveryPasswordFromTokenRequest request,
            @PathVariable String tenant,
            @PathVariable String token

    ) {
        recoveryPasswordFromTokenService.execute(token, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
