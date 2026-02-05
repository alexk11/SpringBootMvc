package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageResponse> handleValidationException(MethodArgumentNotValidException ex) {
        var error = new ErrorMessageResponse("Неверный аргумент: " + ex.getMessage(), "Controller input");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        var error = new ErrorMessageResponse(ex.getMessage(), ex.getOperation());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessageResponse> handleBadRequestException(BadRequestException ex) {
        var error = new ErrorMessageResponse(ex.getMessage(), ex.getOperation());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageResponse> handleException(Exception ex) {
        var error = new ErrorMessageResponse(ex.getMessage(), "Generic");
        if (ex instanceof HttpMessageNotReadableException) {
            error = new ErrorMessageResponse(ex.getMessage(), "Controller input");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
