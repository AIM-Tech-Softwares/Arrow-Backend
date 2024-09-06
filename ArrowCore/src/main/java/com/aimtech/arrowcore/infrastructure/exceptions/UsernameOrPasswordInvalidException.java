package com.aimtech.arrowcore.infrastructure.exceptions;


public class UsernameOrPasswordInvalidException extends RuntimeException {
    public UsernameOrPasswordInvalidException(String message) {
        super(message);
    }

    public UsernameOrPasswordInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
