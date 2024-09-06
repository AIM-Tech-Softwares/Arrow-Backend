package com.aimtech.arrowcore.core.config;

import com.aimtech.arrowcore.core.properties.SecurityProperties;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityProperties securityProperties;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(
//                        auth -> auth
//                                .anyRequest().authenticated()
//                )
                .oauth2ResourceServer(
                        conf -> conf.jwt(
                                jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        String audience = this.securityProperties.getToken().getAudience();
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withPublicKey(
                this.securityProperties.getKey().getPublicKey()
        ).build();

        jwtDecoder.setJwtValidator(JwtValidators.createDefaultWithIssuer(
                this.securityProperties.getToken().getIssuer()
        ));

        jwtDecoder.setJwtValidator((jwt) -> {
            List<String> audiences = jwt.getAudience();
            if (!audiences.contains(audience)) {
                throw new JwtException("Invalid audience: " + audience);
            }
            return OAuth2TokenValidatorResult.success();
        });

        return jwtDecoder;
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(
                this.securityProperties.getKey().getPublicKey()
        ).privateKey(
                this.securityProperties.getKey().getPrivateKey()
        ).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            String scope = jwt.getClaimAsString("scope");
            if (scope == null || scope.isEmpty()) {
                return List.of();
            }

            return Stream.of(scope.split(" "))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        });
        return converter;
    }
}
