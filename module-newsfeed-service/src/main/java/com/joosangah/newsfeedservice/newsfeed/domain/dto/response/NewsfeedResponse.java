package com.joosangah.newsfeedservice.newsfeed.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.joosangah.newsfeedservice.newsfeed.domain.enums.AgentType;
import com.joosangah.newsfeedservice.newsfeed.domain.enums.NewsfeedType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsfeedResponse {

    private String id;
    private String userId;
    private String senderId;
    private NewsfeedType type;
    private AgentType agentType;
    private String postId;
    private LocalDateTime createdAt;
}
