package com.joosangah.ordersystem.newsfeed.domain.dto.response;

import com.joosangah.ordersystem.newsfeed.domain.enums.NewsfeedType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NewsfeedResponse {

    private String userId;
    private String userName;
    private String targetId;
    private String targetName;
    private NewsfeedType type;
    private LocalDateTime createdAt;
}
