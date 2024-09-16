package com.aimtech.arrowcore.domain.business.usecases.admin.user_module;

import com.aimtech.arrowcore.core.utils.AuthUtils;
import com.aimtech.arrowcore.domain.business.dto.responses.admin.UserDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.admin.UserMapper;
import com.aimtech.arrowcore.domain.entities.User;
import com.aimtech.arrowcore.domain.repository.UserRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetCurrentUserService {
    private final AuthUtils authUtils;
    private final UserMapper userMapper;


    @Transactional(readOnly = true)
    public UserDetailResponse execute() {
        User user = authUtils.getCurrentUser();

        return userMapper.toDetailResponse(user);
    }
}
