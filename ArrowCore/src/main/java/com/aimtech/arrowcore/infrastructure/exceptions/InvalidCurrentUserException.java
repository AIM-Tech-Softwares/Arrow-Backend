package com.aimtech.arrowcore.infrastructure.exceptions;

public class InvalidCurrentUserException extends RuntimeException {
    public InvalidCurrentUserException(String message) {
        super(message);
    }

    public InvalidCurrentUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
