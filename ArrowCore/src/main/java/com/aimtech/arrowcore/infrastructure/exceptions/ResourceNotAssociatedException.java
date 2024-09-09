package com.aimtech.arrowcore.infrastructure.exceptions;

public class ResourceNotAssociatedException extends RuntimeException {
    public ResourceNotAssociatedException(String message) {
        super(message);
    }

    public ResourceNotAssociatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
