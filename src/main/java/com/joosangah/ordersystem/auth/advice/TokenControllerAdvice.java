package com.joosangah.ordersystem.auth.advice;

import com.joosangah.ordersystem.common.exception.TokenRefreshException;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class TokenControllerAdvice {

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        return ErrorMessage.builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .timestamp(new Date())
                .message(ex.getMessage())
                .description(request.getDescription(false)).build();
    }
}
