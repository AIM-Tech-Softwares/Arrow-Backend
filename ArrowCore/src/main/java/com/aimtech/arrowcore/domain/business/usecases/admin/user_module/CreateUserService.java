package com.aimtech.arrowcore.domain.business.usecases.admin.user_module;

import com.aimtech.arrowcore.core.utils.AuthUtils;
import com.aimtech.arrowcore.core.utils.IdGenerator;
import com.aimtech.arrowcore.core.utils.PasswordUtils;
import com.aimtech.arrowcore.domain.business.dto.requests.admin.UserRegisterRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.admin.UserRegisterResponse;
import com.aimtech.arrowcore.domain.business.mappers.admin.UserMapper;
import com.aimtech.arrowcore.domain.business.usecases.admin.systemparameter_module.GetSystemParameterService;
import com.aimtech.arrowcore.domain.entities.Profile;
import com.aimtech.arrowcore.domain.entities.User;
import com.aimtech.arrowcore.domain.events.UserCreatedEvent;
import com.aimtech.arrowcore.domain.repository.ProfileRepository;
import com.aimtech.arrowcore.domain.repository.UserRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.DuplicateResourceException;
import com.aimtech.arrowcore.infrastructure.exceptions.InvalidDomainForTenantException;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import com.aimtech.arrowcore.infrastructure.exceptions.UsernameAlreadyExistsException;
import com.aimtech.arrowcore.infrastructure.messaging.message.UserCreateEmailSenderMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreateUserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final MessageSource messageSource;
    private final AuthUtils authUtils;
    private final PasswordUtils passwordUtils;
    private final GetSystemParameterService getSystemParameterService;
    private final ApplicationEventPublisher applicationEventPublisher;

    private static final String ADMIN_USER_USE_INTERNAL_DOMAIN = "admin.user.use_internal_domain";

    @Transactional
    public UserRegisterResponse execute(UserRegisterRequest dto) {
        try {
            User user = prepareUserForRegistration(dto);

            validateUsernameUniqueness(user.getUsername());

            Set<Profile> profiles = getProfilesByIds(dto.getProfileIds());
            user.setProfiles(profiles);

            String tempPassword = passwordUtils.generateRandomPassword(8);
            setUserPasswordAndDefaults(user, tempPassword);

            UserCreateEmailSenderMessage emailSenderMessage = UserCreateEmailSenderMessage.builder()
                    .userEmail(user.getEmail())
                    .firstname(user.getFirstName())
                    .lastname(user.getLastName())
                    .username(user.getUsername())
                    .password(tempPassword)
                    .build();

            user = userRepository.save(user);

            applicationEventPublisher.publishEvent(new UserCreatedEvent(this, emailSenderMessage));

            return userMapper.toResponse(user);
        } catch (DataIntegrityViolationException ex) {
            handleDataIntegrityViolationException(dto, ex);
            throw ex;
        }
    }

    private User prepareUserForRegistration(UserRegisterRequest dto) {
        Boolean useInternalDomain = getSystemParameterService.getBooleanValue(ADMIN_USER_USE_INTERNAL_DOMAIN);

        User user = userMapper.toUser(dto);
        User currentUser = authUtils.getCurrentUser();
        user.setBusinessGroup(currentUser.getBusinessGroup());
        user.setProfiles(new HashSet<>());

        if (useInternalDomain) {
            user.setUsername(user.getUsername() + "@" + currentUser.getBusinessGroup().getTenantDomain());
        } else {
            validateUsername(user.getUsername(), currentUser.getBusinessGroup().getTenantDomain());
        }

        return user;
    }

    private void validateUsernameUniqueness(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistsException(
                    messageSource.getMessage(
                            "arrowcore.exceptions.UsernameAlreadyExistsException",
                            new Object[]{username},
                            LocaleContextHolder.getLocale()
                    )
            );
        }
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

    private void setUserPasswordAndDefaults(User user, String tempPassword) {
        user.setExternalId(IdGenerator.generateExternalId());
        user.setPassword(passwordEncoder.encode(tempPassword));
        user.setEmail(user.getEmail() == null ? user.getUsername() : user.getEmail());
        user.setIsFirstLogin(true);
        user.setIsAccountLocked(false);
        user.setIsAccountExpired(false);
        user.setIsPasswordExpired(false);
    }

    private void validateUsername(String username, String tenantDomain) {
        String userDomain = username.split("@")[1];
        if (!userDomain.equals(tenantDomain)) {
            throw new InvalidDomainForTenantException(
                    messageSource.getMessage(
                            "arrowcore.exceptions.InvalidDomainForTenantException",
                            null,
                            LocaleContextHolder.getLocale()
                    )
            );
        }
    }

    private void handleDataIntegrityViolationException(UserRegisterRequest dto, DataIntegrityViolationException ex) {
        if (ex.getMessage().contains("uc_tb_user_email")) {
            throw new DuplicateResourceException(
                    messageSource.getMessage(
                            "arrowcore.exceptions.DuplicateResourceException",
                            new Object[]{"Email", dto.getEmail(), "User"},
                            LocaleContextHolder.getLocale()
                    )
            );
        }
    }
}