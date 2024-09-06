package com.aimtech.arrowcore.infrastructure.exceptions;

public class InvalidPasswordRecoverTokenException extends RuntimeException {
    public InvalidPasswordRecoverTokenException(String message) {
        super(message);
    }
    public InvalidPasswordRecoverTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
