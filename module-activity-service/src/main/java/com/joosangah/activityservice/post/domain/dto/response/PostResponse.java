package com.joosangah.activityservice.post.domain.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponse {

    private String userId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
