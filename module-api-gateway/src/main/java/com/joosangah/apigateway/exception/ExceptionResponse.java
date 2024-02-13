package com.joosangah.apigateway.exception;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionResponse {

    private final String message;
    private final int status;
    private final HttpStatus error;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime timestamp;

    public ExceptionResponse(String message, HttpStatus httpStatus) {
        this.message = "[ERROR] " + message;
        this.status = httpStatus.value();
        this.error = httpStatus;
        this.timestamp = LocalDateTime.now();
    }
}
