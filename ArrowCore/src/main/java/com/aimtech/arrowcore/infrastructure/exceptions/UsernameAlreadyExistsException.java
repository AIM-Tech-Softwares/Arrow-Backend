package com.aimtech.arrowcore.infrastructure.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
    public UsernameAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
