package com.aimtech.arrowcore.infrastructure.controllers;

import com.aimtech.arrowcore.domain.business.dto.requests.LoginWithUsernameAndPasswordRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.RecoveryPasswordFromTokenRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.RecoveryPasswordRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.LoginWithUsernameAndPasswordResponse;
import com.aimtech.arrowcore.domain.business.usecases.auth_module.GeneratePasswordRecoveryTokenService;
import com.aimtech.arrowcore.domain.business.usecases.auth_module.LoginWithUsernameAndPasswordService;
import com.aimtech.arrowcore.domain.business.usecases.auth_module.RecoveryPasswordFromTokenService;
import com.aimtech.arrowcore.domain.entities.PasswordRecover;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
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
            @PathVariable String tenant,
            @PathVariable String token,
            @Valid @RequestBody RecoveryPasswordFromTokenRequest request
    ) {
        recoveryPasswordFromTokenService.execute(token, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
