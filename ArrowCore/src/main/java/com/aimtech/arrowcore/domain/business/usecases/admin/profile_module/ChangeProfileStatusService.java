package com.aimtech.arrowcore.domain.business.usecases.admin.profile_module;

import com.aimtech.arrowcore.domain.entities.Profile;
import com.aimtech.arrowcore.domain.repository.ProfileRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChangeProfileStatusService {
    private final ProfileRepository profileRepository;
    private final MessageSource messageSource;

    @Transactional
    public void execute(UUID externalId) {
        Profile profile = profileRepository.findByExternalId(externalId).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"Profile", "externalId: " + externalId},
                                LocaleContextHolder.getLocale()
                        )
                )
        );

        profile.setIsActive(!profile.getIsActive());
        profileRepository.save(profile);
    }
}
