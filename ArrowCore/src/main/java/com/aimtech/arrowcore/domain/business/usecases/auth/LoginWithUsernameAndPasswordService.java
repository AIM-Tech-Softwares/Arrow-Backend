package com.aimtech.arrowcore.domain.business.usecases.auth;

import com.aimtech.arrowcore.core.properties.SecurityProperties;
import com.aimtech.arrowcore.domain.business.dto.requests.auth.LoginWithUsernameAndPasswordRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.auth.LoginWithUsernameAndPasswordResponse;
import com.aimtech.arrowcore.domain.entities.User;
import com.aimtech.arrowcore.domain.repository.UserRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.UsernameOrPasswordInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class LoginWithUsernameAndPasswordService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;
    private final GenerateJwtTokenService generateJwtTokenService;
    private final SecurityProperties securityProperties;

    @Transactional
    public LoginWithUsernameAndPasswordResponse execute(LoginWithUsernameAndPasswordRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new UsernameOrPasswordInvalidException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.UsernameOrPasswordInvalidException",
                                null,
                                LocaleContextHolder.getLocale()
                        )
                )
        );
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UsernameOrPasswordInvalidException(
                    messageSource.getMessage(
                            "arrowcore.exceptions.UsernameOrPasswordInvalidException",
                            null,
                            LocaleContextHolder.getLocale()
                    )
            );
        }

        String token = this.generateJwtTokenService.execute(
                new UsernamePasswordAuthenticationToken(user, request.getPassword(), user.getAuthorities()),
                user.getBusinessGroup()
        );
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime expiryAt = now.plusSeconds(this.securityProperties.getToken().getExpiryInSeconds());

        user.setLastLogin(OffsetDateTime.now());
        userRepository.save(user);

        return LoginWithUsernameAndPasswordResponse.builder()
                .accessToken(token)
                .generatedAt(OffsetDateTime.from(now))
                .expiresAt(OffsetDateTime.from(expiryAt))
                .build();
    }


}
