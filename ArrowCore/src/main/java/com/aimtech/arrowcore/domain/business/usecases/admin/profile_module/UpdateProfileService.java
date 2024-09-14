package com.aimtech.arrowcore.domain.business.usecases.admin.profile_module;

import com.aimtech.arrowcore.domain.business.dto.requests.admin.ProfileUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.admin.ProfileDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.admin.ProfileMapper;
import com.aimtech.arrowcore.domain.business.mappers.admin.ProfileUpdateMapper;
import com.aimtech.arrowcore.domain.entities.Profile;
import com.aimtech.arrowcore.domain.entities.Role;
import com.aimtech.arrowcore.domain.repository.ProfileRepository;
import com.aimtech.arrowcore.domain.repository.RoleRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UpdateProfileService {
    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;
    private final ProfileMapper profileMapper;
    private final ProfileUpdateMapper profileUpdateMapper;
    private final MessageSource messageSource;

    @Transactional
    public ProfileDetailResponse execute(ProfileUpdateRequest request, UUID externalId) {
        Profile profile = profileRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundedException(
                        buildNotFoundExceptionMessage("Profile", externalId))
                );

        profileUpdateMapper.updateProfile(request, profile);

        if (request.getRoleIds() != null) {
            Set<Role> roles = getRoles(request.getRoleIds());
            profile.setRoles(roles);
        }

        profile = profileRepository.save(profile);
        return profileMapper.toDetailResponse(profile);
    }

    private Set<Role> getRoles(List<Long> roleIds) {
        return roleIds.stream()
                .map(this::findRoleById)
                .collect(Collectors.toSet());
    }

    private Role findRoleById(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundedException(
                        buildNotFoundExceptionMessage("Role", roleId))
                );
    }

    private String buildNotFoundExceptionMessage(String entity, Object id) {
        return messageSource.getMessage(
                "arrowcore.exceptions.ResourceNotFoundedException",
                new Object[]{entity, "internalId: " + id},
                LocaleContextHolder.getLocale()
        );
    }
}
