package com.aimtech.arrowcore.infrastructure.controllers;

import com.aimtech.arrowcore.core.annotation.CheckSecurity;
import com.aimtech.arrowcore.core.utils.ResourceUriHelper;
import com.aimtech.arrowcore.domain.business.dto.requests.UserRegisterRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.UserRegisterResponse;
import com.aimtech.arrowcore.domain.business.usecases.user_module.CreateUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final CreateUserService createUserService;

    @PostMapping
    @CheckSecurity.User.CanCreate
    public ResponseEntity<Void> registerUser(@RequestBody UserRegisterRequest request) {
        UserRegisterResponse response = this.createUserService.execute(request);

        ResourceUriHelper.addUriInResponseHeader(response.getExternalId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> test() {
        return ResponseEntity.status(HttpStatus.OK).body("teste");
    }
}
