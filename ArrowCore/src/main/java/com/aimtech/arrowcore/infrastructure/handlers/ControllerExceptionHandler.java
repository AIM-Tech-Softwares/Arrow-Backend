package com.aimtech.arrowcore.infrastructure.handlers;

import com.aimtech.arrowcore.domain.business.dto.responses.errors.CustomErrorResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.errors.ValidationErrorResponse;
import com.aimtech.arrowcore.infrastructure.exceptions.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.OffsetDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundedException.class)
    public ResponseEntity<CustomErrorResponse> resourceNotFoundedExceptionHandler(ResourceNotFoundedException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UsernameOrPasswordInvalidException.class)
    public ResponseEntity<CustomErrorResponse> usernameOrPasswordInvalidExceptionHandler(UsernameOrPasswordInvalidException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<CustomErrorResponse> usernameAlreadyExistsExceptionHandler(UsernameAlreadyExistsException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidTenantDomainException.class)
    public ResponseEntity<CustomErrorResponse> invalidTenantDomainExceptionHandler(InvalidTenantDomainException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidPasswordRecoverTokenException.class)
    public ResponseEntity<CustomErrorResponse> invalidPasswordRecoverTokenExceptionHandler(InvalidPasswordRecoverTokenException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<CustomErrorResponse> invalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.PRECONDITION_FAILED;
        String customMessageException = "Please contact our support team for more information.";
        CustomErrorResponse err = getCustomError(status, customMessageException, request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<CustomErrorResponse> noResourceFoundExceptionHandler(NoResourceFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationErrorResponse err = new ValidationErrorResponse(OffsetDateTime.now(), status.value(), status.getReasonPhrase(),"Invalid data sent", request.getRequestURI());

        for (FieldError f: ex.getBindingResult().getFieldErrors()) {
            err.addError(f.getField(), f.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(err);
    }

    private static CustomErrorResponse getCustomError(HttpStatus status, String ex, HttpServletRequest request) {
        return CustomErrorResponse.builder()
                .timestamp(OffsetDateTime.now())
                .httpStatus(status.value())
                .httpError(status.getReasonPhrase())
                .error(ex)
                .path(request.getRequestURI())
                .build();
    }
}
