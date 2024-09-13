package com.aimtech.arrowcore.domain.business.usecases.admin.user_module;

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
public class FindUserByExternalIdService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MessageSource messageSource;


    @Transactional(readOnly = true)
    public UserDetailResponse execute(UUID externalUserId) {
        User user = userRepository.findByExternalId(externalUserId).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"User", "externalId: " + externalUserId},
                                LocaleContextHolder.getLocale()
                        )
                )
        );

        return userMapper.toDetailResponse(user);
    }
}
