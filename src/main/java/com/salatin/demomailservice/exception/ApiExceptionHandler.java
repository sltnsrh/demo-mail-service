package com.salatin.demomailservice.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {
        UsernameAlreadyExistsException.class,
        EmailAlreadyExistsException.class
    })
    public ResponseEntity<ApiExceptionObject> handleConflictException(RuntimeException e) {
        HttpStatus status = HttpStatus.CONFLICT;

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

    private ApiExceptionObject getApiExceptionObject(String message, HttpStatus status) {

        return new ApiExceptionObject(
            message,
            status,
            LocalDateTime.now().toString()
        );
    }
}