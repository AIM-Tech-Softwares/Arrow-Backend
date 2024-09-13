package com.aimtech.arrowcore.infrastructure.exceptions;

public class NewPasswordSameAsOldException extends RuntimeException {
    public NewPasswordSameAsOldException(String message) {
        super(message);
    }

    public NewPasswordSameAsOldException(String message, Throwable cause) {
        super(message, cause);
    }
}
