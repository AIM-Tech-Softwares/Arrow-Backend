package com.aimtech.arrowcore.core.utils;

import com.aimtech.arrowcore.domain.entities.User;
import com.aimtech.arrowcore.domain.repository.UserRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.InvalidCurrentUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtils {
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public static String getUserTenant() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwtPrincipal) {
            return jwtPrincipal.getClaim("tenant");
        } else {
            return null;
        }
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwtPrincipal) {
            String username = jwtPrincipal.getSubject();
            return this.userRepository.findByUsername(username).orElseThrow(
                    () -> new InvalidCurrentUserException(
                            messageSource.getMessage(
                                    "arrowcore.exceptions.InvalidCurrentUserException",
                                    null,
                                    LocaleContextHolder.getLocale()
                            )
                    )
            );
        } else {
            throw new InvalidCurrentUserException(
                    messageSource.getMessage(
                            "arrowcore.exceptions.InvalidCurrentUserException",
                            null,
                            LocaleContextHolder.getLocale()
                    )
            );
        }
    }
}
