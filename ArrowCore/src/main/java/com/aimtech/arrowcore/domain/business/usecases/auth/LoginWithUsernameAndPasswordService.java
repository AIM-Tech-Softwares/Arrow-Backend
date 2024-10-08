package com.aimtech.arrowcore.domain.business.usecases.auth;

import com.aimtech.arrowcore.core.properties.AppProperties;
import com.aimtech.arrowcore.core.properties.SecurityProperties;
import com.aimtech.arrowcore.domain.business.dto.requests.auth.LoginWithUsernameAndPasswordRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.auth.LoginWithUsernameAndPasswordResponse;
import com.aimtech.arrowcore.domain.business.usecases.admin.user_module.CustomUserDetailsService;
import com.aimtech.arrowcore.domain.entities.User;
import com.aimtech.arrowcore.domain.repository.UserRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.AccountIsBlockedException;
import com.aimtech.arrowcore.infrastructure.exceptions.UsernameOrPasswordInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class LoginWithUsernameAndPasswordService {
    private final ApplicationEventPublisher eventPublisher;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;
    private final CustomUserDetailsService customUserDetailsService;
    private final GenerateJwtTokenService generateJwtTokenService;
    private final SecurityProperties securityProperties;
    private final AppProperties appProperties;


    public LoginWithUsernameAndPasswordResponse execute(LoginWithUsernameAndPasswordRequest request) {
       try {
           User user = customUserDetailsService.loadUserByUsername(request.getUsername());

           if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
               throw new UsernameOrPasswordInvalidException(
                       messageSource.getMessage(
                               "arrowcore.exceptions.UsernameOrPasswordInvalidException",
                               null,
                               LocaleContextHolder.getLocale()
                       )
               );
           }

           if (user.getIsAccountLocked()) {
               int LOCK_TIME_DURATION_MILLIS = appProperties.getDefaultValues()
                       .getLoginAttempts().getLockoutDurationInSeconds() * 1000;
               long lockTimeInMillis = System.currentTimeMillis() - user.getLastFailedLoginTime().getTime();
               if (lockTimeInMillis > LOCK_TIME_DURATION_MILLIS) {
                   user.setIsAccountLocked(false);
                   user.setFailedLoginAttempts(0);
                   user.setLastFailedLoginTime(null);
               } else {
                   throw new AccountIsBlockedException(
                           messageSource.getMessage(
                                   "arrowcore.exceptions.AccountIsBlockedException",
                                   null,
                                   LocaleContextHolder.getLocale()
                           )
                   );
               }
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

       } catch (UsernameOrPasswordInvalidException ex) {
           Authentication failedAuthentication = new UsernamePasswordAuthenticationToken(
                   request.getUsername(),
                   request.getPassword()
           );
           eventPublisher.publishEvent(
                   new AuthenticationFailureBadCredentialsEvent(failedAuthentication, ex)
           );
           throw ex;
       }
    }
}
