package com.joosangah.apigateway.payload;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenUser {

    private String id;
    private String role;
}
