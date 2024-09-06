package com.aimtech.arrowcore.domain.business.usecases.auth_module;

import com.aimtech.arrowcore.core.properties.AppProperties;
import com.aimtech.arrowcore.core.utils.PasswordUtils;
import com.aimtech.arrowcore.domain.business.dto.requests.RecoveryPasswordRequest;
import com.aimtech.arrowcore.domain.entities.PasswordRecover;
import com.aimtech.arrowcore.domain.entities.User;
import com.aimtech.arrowcore.domain.repository.PasswordRecoveryRepository;
import com.aimtech.arrowcore.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class GeneratePasswordRecoveryTokenService {
    private static final Logger logger = Logger.getLogger(GeneratePasswordRecoveryTokenService.class.getName());

    private final PasswordRecoveryRepository passwordRecoveryRepository;
    private final UserRepository userRepository;
    private final AppProperties appProperties;
    private final PasswordUtils passwordUtils;
    private final FindBusinessGroupByUsernameDomainService findBusinessGroupByUsernameDomainService;

    @Transactional
    public void execute(RecoveryPasswordRequest request) {
        try {
            Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
            if (optionalUser.isEmpty()) {
                return;
            }
            this.invalidateOldUnusedTokensFromUser(request.getUsername());

            Instant now = Instant.now();
            Integer tokenExpirationInSeconds = appProperties.getDefaultValues().getRecoveryToken().getExpiryInSeconds();
            User user = optionalUser.get();
            String token = passwordUtils.generateHashedToken(user.getExternalId());

            PasswordRecover entity = PasswordRecover.builder()
                    .username(user.getUsername())
                    .token(token)
                    .expiration(now.plusSeconds(tokenExpirationInSeconds))
                    .createdAt(OffsetDateTime.now())
                    .build();

            PasswordRecover inserted = passwordRecoveryRepository.save(entity);
            String redirectUri = this.appProperties.getDefaultValues().getRecoveryToken().getRedirectUri();
            String redirectUrl = String.format("%s/%s/%s", redirectUri, user.getBusinessGroup().getSchemaName(), inserted.getToken());
            logger.info("Generated token: " + redirectUrl);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void invalidateOldUnusedTokensFromUser(String username) {
        List<PasswordRecover> listTokenValid = passwordRecoveryRepository.searchValidTokensFromEmail(
                username, Instant.now());
        List<PasswordRecover> tokensToInvalidate = new ArrayList<>();

        for (PasswordRecover entity : listTokenValid) {
            entity.setUsedAt(OffsetDateTime.now());
            tokensToInvalidate.add(entity);
        }

        passwordRecoveryRepository.saveAll(tokensToInvalidate);
    }
}
