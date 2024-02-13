package com.joosangah.apigateway.exception;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> objectErrors = bindingResult.getAllErrors();

        ObjectError firstObjectError = objectErrors.get(0);

        String fieldName = bindingResult.getFieldErrors().size() > 0 ? bindingResult.getFieldErrors().get(0).getField() : firstObjectError.getObjectName();

        String errorMessage = Arrays.stream(firstObjectError.getCodes()).toList().contains("typeMismatch") ?
                "값이 잘못된 형식입니다" : fieldName + " : " + firstObjectError.getDefaultMessage();

        ExceptionResponse response = new ExceptionResponse("[ERROR] " + errorMessage,
                HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleException(AccessDeniedException e) {
        ExceptionResponse response = new ExceptionResponse("[ERROR] " + e.getMessage(),
                HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleException(NoSuchElementException e) {
        ExceptionResponse response = new ExceptionResponse("[ERROR] " + e.getMessage(),
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        ExceptionResponse response = new ExceptionResponse("[ERROR] " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
