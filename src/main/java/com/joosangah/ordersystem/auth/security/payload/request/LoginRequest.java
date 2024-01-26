package com.joosangah.ordersystem.auth.security.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
