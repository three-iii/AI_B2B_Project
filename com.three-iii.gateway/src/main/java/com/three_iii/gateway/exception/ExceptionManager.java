package com.three_iii.gateway.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j(topic = "Rest Controller Advice")

@RestControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> runtimeExceptionHandler(AuthException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(e.getHttpStatus())
            .body(Response.error(e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Response.error(e.getMessage()));
    }
}
