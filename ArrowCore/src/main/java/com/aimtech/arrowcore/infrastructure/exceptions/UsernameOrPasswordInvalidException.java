package com.aimtech.arrowcore.infrastructure.exceptions;


import org.springframework.security.core.AuthenticationException;

public class UsernameOrPasswordInvalidException extends AuthenticationException {
    public UsernameOrPasswordInvalidException(String message) {
        super(message);
    }

    public UsernameOrPasswordInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
