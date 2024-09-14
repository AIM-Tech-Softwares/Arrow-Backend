package com.aimtech.arrowcore.domain.business.usecases.admin.profile_module;


import com.aimtech.arrowcore.domain.business.dto.responses.ProfileSummaryResponse;
import com.aimtech.arrowcore.domain.business.mappers.admin.ProfileMapper;
import com.aimtech.arrowcore.domain.entities.Profile;
import com.aimtech.arrowcore.domain.enums.FilterStatusEnum;
import com.aimtech.arrowcore.domain.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindAllProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Transactional(readOnly = true)
    public Page<ProfileSummaryResponse> execute(Pageable pageable, FilterStatusEnum status) {
        Page<Profile> profilePage;

        switch (status) {
            case ENABLED -> profilePage = profileRepository.findAllByIsActive(pageable, true);
            case DISABLED -> profilePage = profileRepository.findAllByIsActive(pageable, false);
            default -> profilePage = profileRepository.findAll(pageable);
        }

        return profilePage.map(profileMapper::toSummaryResponse);
    }
}
