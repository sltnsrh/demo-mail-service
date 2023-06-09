package com.salatin.demomailservice.exception;

import jakarta.mail.AuthenticationFailedException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.SchedulingException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    private final static String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @ExceptionHandler(value = EntityExistsException.class)
    public ResponseEntity<ApiExceptionObject> handleConflictException(RuntimeException e) {
        HttpStatus status = HttpStatus.CONFLICT;

        return new ResponseEntity<>(getApiExceptionObject(e.getMessage(), status), status);
    }

    @ExceptionHandler(value = {
        ValidationException.class,
        PropertyReferenceException.class,
        IllegalStateException.class
    })
    public ResponseEntity<ApiExceptionObject> handleValidationConflictException(
        RuntimeException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(getApiExceptionObject(e.getMessage(), status), status);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionObject> handleBadRequestException(
        MethodArgumentNotValidException e
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errorMessages = e.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(", "));

        return new ResponseEntity<>(getApiExceptionObject(errorMessages, status), status);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ApiExceptionObject> handleResourceNotFoundException(RuntimeException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(getApiExceptionObject(e.getMessage(), status), status);
    }

    @ExceptionHandler(value = SchedulingException.class)
    public ResponseEntity<ApiExceptionObject> handleAcceptedException(RuntimeException e) {
        HttpStatus status = HttpStatus.ACCEPTED;

        return new ResponseEntity<>(getApiExceptionObject(e.getMessage(), status), status);
    }

    @ExceptionHandler(value = AuthenticationFailedException.class)
    public ResponseEntity<ApiExceptionObject> handleUnauthorizedException(RuntimeException e) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return new ResponseEntity<>(getApiExceptionObject(e.getMessage(), status), status);
    }

    private ApiExceptionObject getApiExceptionObject(String message, HttpStatus status) {

        return new ApiExceptionObject(
            message,
            status,
            LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN))
        );
    }
}
