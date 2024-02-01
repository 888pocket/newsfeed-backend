package com.joosangah.ordersystem.post.domain.dto.response;

import com.joosangah.ordersystem.user.domain.dto.response.UserResponse;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostSummaryResponse {

    private String id;
    private String title;
    private UserResponse user;
    private LocalDateTime createdAt;
}
