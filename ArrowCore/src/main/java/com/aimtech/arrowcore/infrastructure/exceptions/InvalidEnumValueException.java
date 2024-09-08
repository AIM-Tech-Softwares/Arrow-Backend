package com.aimtech.arrowcore.infrastructure.exceptions;

public class InvalidEnumValueException extends RuntimeException {
    public InvalidEnumValueException(String message) {
        super(message);
    }

    public InvalidEnumValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
