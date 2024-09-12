package com.aimtech.arrowcore.domain.business.usecases.auth;

import com.aimtech.arrowcore.core.properties.SecurityProperties;
import com.aimtech.arrowcore.domain.entities.BusinessGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenerateJwtTokenService {
    private final JwtEncoder jwtEncoder;
    private final SecurityProperties securityProperties;

    @Transactional
    public String execute(Authentication authentication, BusinessGroup businessGroup) {
        Instant now = Instant.now();
        Long expiry = this.securityProperties.getToken().getExpiryInSeconds();
        Instant expiryAt = now.plusSeconds(expiry);
        List<String> audience = new ArrayList<>();
        audience.add(this.securityProperties.getToken().getAudience());

        String scope = authentication
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.
                        joining(" ")
                );

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(this.securityProperties.getToken().getIssuer())
                .issuedAt(now)
                .expiresAt(expiryAt)
                .subject(authentication.getName())
                .audience(audience)
                .claim("tenant", businessGroup.getSchemaName())
                .claim("scope", scope)
                .build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(claims)
        ).getTokenValue();
    }
}
