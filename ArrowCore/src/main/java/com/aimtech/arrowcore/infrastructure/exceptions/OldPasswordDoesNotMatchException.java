package com.aimtech.arrowcore.infrastructure.exceptions;

public class OldPasswordDoesNotMatchException extends RuntimeException {
    public OldPasswordDoesNotMatchException(String message) {
        super(message);
    }

    public OldPasswordDoesNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
