package com.joosangah.ordersystem.user.domain.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private String name;
    private String email;
    private String profileImageUrl;
    private String introduction;
    private LocalDateTime createdAt;
}
