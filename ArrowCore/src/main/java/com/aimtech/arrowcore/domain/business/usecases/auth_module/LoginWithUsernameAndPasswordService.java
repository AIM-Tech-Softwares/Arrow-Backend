package com.aimtech.arrowcore.domain.business.usecases.auth_module;

import com.aimtech.arrowcore.core.properties.SecurityProperties;
import com.aimtech.arrowcore.domain.business.dto.requests.LoginWithUsernameAndPasswordRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.LoginWithUsernameAndPasswordResponse;
import com.aimtech.arrowcore.domain.entities.User;
import com.aimtech.arrowcore.domain.repository.BusinessGroupRepository;
import com.aimtech.arrowcore.domain.repository.UserRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.UsernameOrPasswordInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class LoginWithUsernameAndPasswordService {
    private final UserRepository userRepository;
    private final BusinessGroupRepository businessGroupRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenerateJwtTokenService generateJwtTokenService;
    private final SecurityProperties securityProperties;

    @Transactional
    public LoginWithUsernameAndPasswordResponse execute(LoginWithUsernameAndPasswordRequest request) {
        // Fetch BusinessGroup based on username
//        String tenantDomain = businessGroupRepository.searchTenantDomainByUsername(request.getUsername()).orElseThrow(
//                () -> new UsernameNotFoundException("Tenant not found for user: " + request.getUsername())
//        );

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new UsernameOrPasswordInvalidException("Invalid username or password")
        );
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UsernameOrPasswordInvalidException("Invalid username or password");
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
