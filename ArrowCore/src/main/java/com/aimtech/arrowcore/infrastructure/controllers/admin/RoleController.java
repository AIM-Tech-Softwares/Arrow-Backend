package com.aimtech.arrowcore.infrastructure.controllers.admin;

import com.aimtech.arrowcore.core.annotation.CheckSecurity;
import com.aimtech.arrowcore.domain.business.dto.responses.admin.RoleSummaryResponse;
import com.aimtech.arrowcore.domain.business.usecases.admin.role_module.FindAllRolesService;
import com.aimtech.arrowcore.infrastructure.openapi.admin.RoleControllerOpenAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/roles")
public class RoleController implements RoleControllerOpenAPI {
    private final FindAllRolesService findAllRolesService;

    @Override
    @GetMapping
    @CheckSecurity.Role.CanRead
    public ResponseEntity<List<RoleSummaryResponse>> findAll() {
        List<RoleSummaryResponse> result = findAllRolesService.execute();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
