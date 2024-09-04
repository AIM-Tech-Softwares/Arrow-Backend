package com.aimtech.arrowcore.infrastructure.controllers;

import com.aimtech.arrowcore.domain.business.dto.requests.UserRegisterRequest;
import com.aimtech.arrowcore.domain.business.usecases.user_module.CreateUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final CreateUserService createUserService;

    @PostMapping("/register-new")
    public ResponseEntity<Void> registerUser(@RequestBody UserRegisterRequest request) {
        this.createUserService.execute(request);
        return ResponseEntity.ok().build();
    }
}
