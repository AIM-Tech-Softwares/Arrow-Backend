package com.aimtech.arrowcore.domain.business.usecases.auth;

import com.aimtech.arrowcore.core.properties.AppProperties;
import com.aimtech.arrowcore.domain.entities.User;
import com.aimtech.arrowcore.domain.repository.UserRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.AccountIsBlockedException;
import com.aimtech.arrowcore.infrastructure.exceptions.UsernameOrPasswordInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class FailureLoginRegisterService {
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final AppProperties appProperties;


    public void execute(String username) {
        int MAX_ATTEMPTS = appProperties.getDefaultValues().getLoginAttempts().getMaxAttempts();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameOrPasswordInvalidException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.UsernameOrPasswordInvalidException",
                                null,
                                LocaleContextHolder.getLocale()
                        )
                )
        );

        if (isAccountLocked(user)) {
            throw new AccountIsBlockedException(
                    messageSource.getMessage(
                            "arrowcore.exceptions.AccountIsBlockedException",
                            null,
                            LocaleContextHolder.getLocale()
                    )
            );
        }

        if (user.getFailedLoginAttempts() == null) {
            user.setFailedLoginAttempts(1);
        } else {
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
        }

        user.setLastFailedLoginTime(new Date());

        if (user.getFailedLoginAttempts() >= MAX_ATTEMPTS) {
            user.setIsAccountLocked(true);
        }

        userRepository.save(user);
    }


    private boolean isAccountLocked(User user) {
        int LOCK_TIME_DURATION_MILLIS = appProperties.getDefaultValues()
                .getLoginAttempts().getLockoutDurationInSeconds() * 1000;

        if (user.getIsAccountLocked() == null || !user.getIsAccountLocked()) {
            return false;
        }

        long lockTimeInMillis = System.currentTimeMillis() - user.getLastFailedLoginTime().getTime();
        if (lockTimeInMillis > LOCK_TIME_DURATION_MILLIS) {
            user.setIsAccountLocked(false);
            user.setFailedLoginAttempts(0);
            user.setLastFailedLoginTime(null);
            userRepository.save(user);
            return false;
        }

        return true;
    }
}
