package com.aimtech.arrowcore.domain.business.usecases.admin.user_module;

import com.aimtech.arrowcore.core.utils.AuthUtils;
import com.aimtech.arrowcore.domain.business.dto.requests.admin.ChangeUserPasswordRequest;
import com.aimtech.arrowcore.domain.entities.User;
import com.aimtech.arrowcore.domain.repository.UserRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.NewPasswordSameAsOldException;
import com.aimtech.arrowcore.infrastructure.exceptions.OldPasswordDoesNotMatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ChangeUserPasswordService {
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtils authUtils;

    @Transactional
    public void execute(ChangeUserPasswordRequest request) {
        User currentUser = authUtils.getCurrentUser();

        String storedPassword = currentUser.getPassword();
        String inputOldPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();

        if (!passwordEncoder.matches(inputOldPassword, storedPassword)) {
            throw new OldPasswordDoesNotMatchException(
                    messageSource.getMessage(
                            "arrowcore.exceptions.OldPasswordDoesNotMatchException",
                            null,
                            LocaleContextHolder.getLocale()
                    )
            );
        }

        if (passwordEncoder.matches(newPassword, storedPassword)) {
            throw new NewPasswordSameAsOldException(
                    messageSource.getMessage(
                            "arrowcore.exceptions.NewPasswordSameAsOldException",
                            null,
                            LocaleContextHolder.getLocale()
                    )
            );
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        currentUser.setPassword(encodedPassword);
        currentUser.setLastPasswordChangeDate(new Date());
        userRepository.save(currentUser);
    }
}
