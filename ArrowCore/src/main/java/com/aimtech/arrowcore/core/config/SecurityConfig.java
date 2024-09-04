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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
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
                                jwt -> jwt.decoder(jwtDecoder())
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
            return null;
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
}
