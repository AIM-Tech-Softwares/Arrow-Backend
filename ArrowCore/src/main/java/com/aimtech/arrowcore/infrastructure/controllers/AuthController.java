package com.aimtech.arrowcore.infrastructure.controllers;

import com.aimtech.arrowcore.domain.business.dto.requests.LoginWithUsernameAndPasswordRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.LoginWithUsernameAndPasswordResponse;
import com.aimtech.arrowcore.domain.business.usecases.auth_module.LoginWithUsernameAndPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
    private final LoginWithUsernameAndPasswordService loginWithUsernameAndPasswordService;

    @PostMapping("/login")
    public ResponseEntity<LoginWithUsernameAndPasswordResponse> loginWithUsernameAndPassword(
            @RequestBody LoginWithUsernameAndPasswordRequest request
    ) {
        LoginWithUsernameAndPasswordResponse result = this.loginWithUsernameAndPasswordService.execute(request);
        return ResponseEntity.ok(result);
    }
}
