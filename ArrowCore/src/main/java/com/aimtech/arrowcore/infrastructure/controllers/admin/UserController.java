package com.aimtech.arrowcore.infrastructure.controllers.admin;

import com.aimtech.arrowcore.core.annotation.CheckSecurity;
import com.aimtech.arrowcore.core.utils.ResourceUriHelper;
import com.aimtech.arrowcore.domain.business.dto.requests.admin.UserRegisterRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.admin.UserRegisterResponse;
import com.aimtech.arrowcore.domain.business.usecases.admin.user_module.CreateUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class UserController {
    private final CreateUserService createUserService;

    @PostMapping
    @CheckSecurity.User.CanCreate
    public ResponseEntity<Void> registerUser(@Valid @RequestBody UserRegisterRequest request) {
        UserRegisterResponse response = this.createUserService.execute(request);

        ResourceUriHelper.addUriInResponseHeader(response.getExternalId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
