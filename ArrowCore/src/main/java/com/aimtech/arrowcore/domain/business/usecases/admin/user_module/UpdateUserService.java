package com.aimtech.arrowcore.domain.business.usecases.admin.user_module;

import com.aimtech.arrowcore.domain.business.dto.requests.admin.UserUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.admin.UserDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.admin.UserMapper;
import com.aimtech.arrowcore.domain.business.mappers.admin.UserUpdateMapper;
import com.aimtech.arrowcore.domain.entities.Profile;
import com.aimtech.arrowcore.domain.entities.User;
import com.aimtech.arrowcore.domain.repository.ProfileRepository;
import com.aimtech.arrowcore.domain.repository.UserRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.DuplicateResourceException;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UpdateUserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final UserMapper userMapper;
    private final UserUpdateMapper userUpdateMapper;
    private final MessageSource messageSource;

    @Transactional
    public UserDetailResponse execute(UserUpdateRequest request, UUID externalUserId) {
        User user = userRepository.findByExternalId(externalUserId).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"User", "externalId: " + externalUserId},
                                LocaleContextHolder.getLocale()
                        )
                )
        );

        if (!Objects.equals(user.getEmail(), request.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateResourceException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.DuplicateResourceException",
                                new Object[]{"Email", request.getEmail(), "Other User"},
                                LocaleContextHolder.getLocale()
                        )
                );
            }
        }

        userUpdateMapper.updateUser(request, user);

        Set<Profile> profiles = getProfilesByIds(request.getProfileIds());
        user.setProfiles(profiles);

        userRepository.save(user);

        return userMapper.toDetailResponse(user);
    }

    private Set<Profile> getProfilesByIds(List<Long> profileIds) {
        Set<Profile> profiles = new HashSet<>();
        for (Long profileId : profileIds) {
            Profile profile = profileRepository.findById(profileId).orElseThrow(
                    () -> new ResourceNotFoundedException(
                            messageSource.getMessage(
                                    "arrowcore.exceptions.ResourceNotFoundedException",
                                    new Object[]{"Profile", "ID: " + profileId},
                                    LocaleContextHolder.getLocale()
                            )
                    )
            );
            profiles.add(profile);
        }
        return profiles;
    }
}
