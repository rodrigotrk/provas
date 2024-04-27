package com.applydigital.hackernews.infrastructure.api.controller;

import com.applydigital.hackernews.domain.exceptions.DomainException;
import com.applydigital.hackernews.domain.validation.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(value = NotFoundException.class)
//    public ResponseEntity<?> handleNotFoundException(final NotFoundException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.from(ex));
//    }

    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<?> handleDomainException(final DomainException ex) {
        return ResponseEntity.unprocessableEntity().body(ApiError.from(ex));
    }

    record ApiError(String message, List<Error> errors) {
        static ApiError from(final DomainException ex) {
            return new ApiError(ex.getMessage(), ex.getErrors());
        }
    }
}