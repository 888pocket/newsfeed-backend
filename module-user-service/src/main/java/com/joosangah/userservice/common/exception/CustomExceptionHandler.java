package com.joosangah.userservice.common.exception;

import com.joosangah.userservice.common.domain.ExceptionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class CustomExceptionHandler {

    @Value("${spring.servlet.multipart.max-file-size}")
    String maxFileSize;

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException e) {
        long maxUploadSize = e.getMaxUploadSize();

        HttpStatus status = HttpStatus.PAYLOAD_TOO_LARGE;
        ExceptionResponse response = new ExceptionResponse(
                String.format("File size exceeds the limit of %s",
                        maxUploadSize == -1 ? maxFileSize : maxUploadSize),
                status);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleTokenRefreshException(TokenRefreshException e) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ExceptionResponse response = new ExceptionResponse(e.getMessage(),
                status);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<Object> handleDuplicateUserIdException(
            DuplicateException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        ExceptionResponse response = new ExceptionResponse(e.getMessage(),
                status);
        return new ResponseEntity<>(response, status);
    }
}