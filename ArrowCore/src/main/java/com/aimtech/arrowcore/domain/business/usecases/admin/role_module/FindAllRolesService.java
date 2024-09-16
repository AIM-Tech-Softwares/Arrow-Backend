package com.aimtech.arrowcore.domain.business.usecases.admin.role_module;

import com.aimtech.arrowcore.domain.business.dto.responses.admin.RoleSummaryResponse;
import com.aimtech.arrowcore.domain.business.mappers.admin.RoleMapper;
import com.aimtech.arrowcore.domain.entities.Role;
import com.aimtech.arrowcore.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllRolesService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Transactional(readOnly = true)
    public List<RoleSummaryResponse> execute() {
        List<Role> roles = roleRepository.findAll();

        return roles.stream()
                .map(roleMapper::toSummaryResponse).toList();
    }
}
