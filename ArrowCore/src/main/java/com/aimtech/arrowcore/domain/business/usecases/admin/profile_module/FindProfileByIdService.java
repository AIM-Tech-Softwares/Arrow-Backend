package com.aimtech.arrowcore.domain.business.usecases.admin.profile_module;

import com.aimtech.arrowcore.domain.business.dto.responses.admin.ProfileDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.admin.ProfileMapper;
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
public class FindProfileByIdService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final MessageSource messageSource;

    @Transactional(readOnly = true)
    public ProfileDetailResponse execute(UUID externalId) {
        Profile profile = profileRepository.findByExternalId(externalId).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"Profile", "internalId: " + externalId},
                                LocaleContextHolder.getLocale()
                        )
                )
        );
        return profileMapper.toDetailResponse(profile);
    }
}
