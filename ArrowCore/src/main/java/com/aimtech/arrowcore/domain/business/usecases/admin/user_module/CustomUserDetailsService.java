package com.aimtech.arrowcore.domain.business.usecases.admin.user_module;

import com.aimtech.arrowcore.domain.entities.User;
import com.aimtech.arrowcore.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Override
    @Transactional(readOnly = true)
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.InvalidCurrentUserException",
                                null,
                                LocaleContextHolder.getLocale()
                        ))
                );
        if (!user.isEnabled()) {
            throw new DisabledException(
                    messageSource.getMessage(
                            "arrowcore.exceptions.UsernameOrPasswordInvalidException",
                            null,
                            LocaleContextHolder.getLocale()
                    )
            );
        }

        if (!user.isAccountNonLocked()) {
            throw new LockedException(
                    messageSource.getMessage(
                            "arrowcore.exceptions.AccountIsBlockedException",
                            null,
                            LocaleContextHolder.getLocale()
                    )
            );
        }

        return user;
    }
}
