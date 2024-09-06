package com.aimtech.arrowcore.infrastructure.exceptions;

public class ResourceNotFoundedException extends RuntimeException {

    public ResourceNotFoundedException(String message) {
        super(message);
    }

    public ResourceNotFoundedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundedException(String entityName, String searchParam, String param) {
        super(String.format("Entity: %s, with param: '%s', value: '%s' not found", entityName, searchParam, param));
    }

    public ResourceNotFoundedException(String entityName, String searchParam, String param, Throwable cause) {
        super(
                String.format("Entity: %s, with param: '%s', value: '%s' not found", entityName, searchParam, param),
                cause
        );
    }
}
