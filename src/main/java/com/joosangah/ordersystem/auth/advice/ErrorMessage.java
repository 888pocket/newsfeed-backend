package com.joosangah.ordersystem.auth.advice;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorMessage {

    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;
}
