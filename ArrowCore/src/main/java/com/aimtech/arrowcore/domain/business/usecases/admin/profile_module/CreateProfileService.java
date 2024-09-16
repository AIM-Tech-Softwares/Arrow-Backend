package com.aimtech.arrowcore.domain.business.usecases.admin.profile_module;

import com.aimtech.arrowcore.core.utils.IdGenerator;
import com.aimtech.arrowcore.domain.business.dto.requests.admin.ProfileCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.admin.ProfileDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.admin.ProfileMapper;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final RoleRepository roleRepository;
    private final MessageSource messageSource;

    @Transactional
    public ProfileDetailResponse execute(ProfileCreateRequest request) {
        Profile profile = profileMapper.toEntity(request);

        Set<Role> roles = getRoles(request.getRoleIds());
        profile.setRoles(roles);
        profile.setExternalId(IdGenerator.generateExternalId());

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
                .orElseThrow(
                        () -> new ResourceNotFoundedException(
                            messageSource.getMessage(
                                    "arrowcore.exceptions.ResourceNotFoundedException",
                                    new Object[]{"Role", "internalId: " + roleId},
                                    LocaleContextHolder.getLocale()
                            )
                        )
                );
    }
}
