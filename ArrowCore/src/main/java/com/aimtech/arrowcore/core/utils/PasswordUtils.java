package com.aimtech.arrowcore.core.utils;

import com.aimtech.arrowcore.core.properties.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class PasswordUtils {
    private final AppProperties appProperties;

    private static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";

    private static final String ALL_CHARS = UPPERCASE_CHARS + LOWERCASE_CHARS + DIGITS;

    private static final SecureRandom RANDOM = new SecureRandom();

    public String generateRandomPassword(int length) {
        Integer minimumPasswordLength = appProperties.getDefaultValues().getMinimumPasswordLength();
        if (length < minimumPasswordLength) {
            throw new IllegalArgumentException(String.format(
                    "Password length should be at least %s characters",
                    minimumPasswordLength
            ));
        }

        StringBuilder password = new StringBuilder(length);

        password.append(UPPERCASE_CHARS.charAt(RANDOM.nextInt(UPPERCASE_CHARS.length())));
        password.append(LOWERCASE_CHARS.charAt(RANDOM.nextInt(LOWERCASE_CHARS.length())));
        password.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));

        for (int i = 3; i < length; i++) {
            password.append(ALL_CHARS.charAt(RANDOM.nextInt(ALL_CHARS.length())));
        }

        return shuffleString(password.toString());
    }

    private static String shuffleString(String input) {
        char[] a = input.toCharArray();
        for (int i = a.length - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            char temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
        return new String(a);
    }
}
