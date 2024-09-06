package com.aimtech.arrowcore.domain.business.usecases.auth_module;

import com.aimtech.arrowcore.domain.business.dto.requests.RecoveryPasswordFromTokenRequest;
import com.aimtech.arrowcore.domain.entities.PasswordRecover;
import com.aimtech.arrowcore.domain.entities.User;
import com.aimtech.arrowcore.domain.repository.PasswordRecoveryRepository;
import com.aimtech.arrowcore.domain.repository.UserRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.InvalidPasswordRecoverTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RecoveryPasswordFromTokenService {
    private final PasswordRecoveryRepository passwordRecoveryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(String token, RecoveryPasswordFromTokenRequest request) {
        PasswordRecover passwordRecover = passwordRecoveryRepository.searchValidToken(token, Instant.now()).orElseThrow(
                () -> new InvalidPasswordRecoverTokenException("Invalid token")
        );

        User user = userRepository.findByUsername(passwordRecover.getUsername()).orElseThrow(
                () -> {
                    passwordRecover.setUsedAt(OffsetDateTime.now());
                    passwordRecoveryRepository.save(passwordRecover);
                    return new InvalidPasswordRecoverTokenException("Token not correspond to user");
                }
        );

        if (!Objects.equals(passwordRecover.getUsername(), user.getUsername())) {
            passwordRecover.setUsedAt(OffsetDateTime.now());
            passwordRecoveryRepository.save(passwordRecover);
            throw  new InvalidPasswordRecoverTokenException("Token not correspond to user");
        }

        passwordRecover.setUsedAt(OffsetDateTime.now());
        passwordRecoveryRepository.save(passwordRecover);
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
