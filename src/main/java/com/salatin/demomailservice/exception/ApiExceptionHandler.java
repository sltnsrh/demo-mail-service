package com.salatin.demomailservice.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private ApiExceptionObject getApiExceptionObject(String message, HttpStatus status) {

        return new ApiExceptionObject(
            message,
            status,
            LocalDateTime.now().toString()
        );
    }
}
