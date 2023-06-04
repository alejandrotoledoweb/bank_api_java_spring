package com.bankapi.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorObject> handleClientNotFoundException(ClientNotFoundException ex, WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(ReviewNotFoundException.class)
//    public ResponseEntity<ErrorObject> handleReviewNotFoundException(ReviewNotFoundException ex, WebRequest request) {
//        ErrorObject errorObject = new ErrorObject();
//
//        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
//        errorObject.setMessage(ex.getMessage());
//        errorObject.setTimestamp(new Date());
//
//        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
//    }
}
