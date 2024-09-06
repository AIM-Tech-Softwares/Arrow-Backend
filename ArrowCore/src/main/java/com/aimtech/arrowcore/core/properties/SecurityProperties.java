package com.aimtech.arrowcore.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Getter
@Setter
@Component
@ConfigurationProperties("arrowcore.security")
public class SecurityProperties {

    private final KeyProps key = new KeyProps();
    private final TokenProps token = new TokenProps();

    @Getter
    @Setter
    public static class KeyProps {
        private RSAPublicKey publicKey;
        private RSAPrivateKey privateKey;
    }

    @Getter
    @Setter
    public static class TokenProps {
        private Long expiryInSeconds;
        private String issuer;
        private String audience;
    }
}
