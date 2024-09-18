package com.aimtech.arrowcore.domain.business.usecases.auth;

import com.aimtech.arrowcore.core.properties.AppProperties;
import com.aimtech.arrowcore.core.utils.PasswordUtils;
import com.aimtech.arrowcore.domain.business.dto.requests.auth.RecoveryPasswordRequest;
import com.aimtech.arrowcore.domain.entities.PasswordRecover;
import com.aimtech.arrowcore.domain.entities.User;
import com.aimtech.arrowcore.domain.events.RecoveryUserPasswordEvent;
import com.aimtech.arrowcore.domain.repository.PasswordRecoveryRepository;
import com.aimtech.arrowcore.domain.repository.UserRepository;
import com.aimtech.arrowcore.infrastructure.messaging.message.PasswordRecoveryNotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
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
    private final ApplicationEventPublisher applicationEventPublisher;

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
            String token = passwordUtils.generateHashedToken(user.getExternalId().toString());
            Instant expirationDate = now.plusSeconds(tokenExpirationInSeconds);

            PasswordRecover entity = PasswordRecover.builder()
                    .username(user.getUsername())
                    .token(token)
                    .expiration(expirationDate)
                    .createdAt(OffsetDateTime.now())
                    .build();

            PasswordRecover inserted = passwordRecoveryRepository.save(entity);
            String redirectUri = this.appProperties.getDefaultValues().getRecoveryToken().getRedirectUri();
            String redirectUrl = String.format(
                    "%s/%s/%s",
                    redirectUri,
                    user.getBusinessGroup().getSchemaName(),
                    inserted.getToken()
            );

            PasswordRecoveryNotificationMessage message = PasswordRecoveryNotificationMessage.builder()
                    .fistName(user.getFirstName())
                    .lastName(user.getLastName())
                    .tenantDomain(user.getBusinessGroup().getTenantDomain())
                    .redirectUrl(redirectUrl)
                    .expirationDate(Date.from(expirationDate))
                    .email(user.getEmail())
                    .build();
            applicationEventPublisher.publishEvent(new RecoveryUserPasswordEvent(this, message));

        } catch (Exception e) {
            logger.warning(e.getMessage());
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
