package com.joosangah.ordersystem.auth.domain.enums;

import org.springframework.security.core.GrantedAuthority;

public enum ERole implements GrantedAuthority {
    USER,
    MODERATOR,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
