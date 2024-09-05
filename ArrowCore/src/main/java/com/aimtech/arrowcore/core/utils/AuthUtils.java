package com.aimtech.arrowcore.core.utils;

import com.aimtech.arrowcore.domain.entities.User;
import com.aimtech.arrowcore.domain.repository.UserRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.InvalidCurrentUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtils {
    private final UserRepository userRepository;

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
                            "Error while trying to retrieve the logged-in user, please log out of the system and log in again."
                    )
            );
        } else {
            throw new InvalidCurrentUserException(
                    "Error while trying to retrieve the logged-in user, please log out of the system and log in again."
            );
        }
    }
}
