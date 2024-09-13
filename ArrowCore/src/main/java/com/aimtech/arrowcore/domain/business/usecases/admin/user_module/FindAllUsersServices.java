package com.aimtech.arrowcore.domain.business.usecases.admin.user_module;

import com.aimtech.arrowcore.domain.business.dto.responses.admin.UserSummaryResponse;
import com.aimtech.arrowcore.domain.business.mappers.admin.UserMapper;
import com.aimtech.arrowcore.domain.entities.User;
import com.aimtech.arrowcore.domain.enums.FilterStatusEnum;
import com.aimtech.arrowcore.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAllUsersServices {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Page<UserSummaryResponse> execute(Pageable pageable, FilterStatusEnum status) {
        Page<User> userPage;

        switch (status) {
            case ENABLED -> userPage = userRepository.findAllByIsActive(pageable, true);
            case DISABLED -> userPage = userRepository.findAllByIsActive(pageable, false);
            default -> userPage = userRepository.findAll(pageable);
        }

        return userPage.map(userMapper::toSummaryResponse);
    }
}
