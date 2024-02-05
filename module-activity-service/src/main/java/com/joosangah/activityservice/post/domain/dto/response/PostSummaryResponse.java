package com.joosangah.activityservice.post.domain.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostSummaryResponse {

    private String id;
    private String title;
    private String userId;
    private LocalDateTime createdAt;
}
