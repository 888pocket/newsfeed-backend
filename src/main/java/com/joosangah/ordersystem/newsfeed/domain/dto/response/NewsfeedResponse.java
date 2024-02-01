package com.joosangah.ordersystem.newsfeed.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.joosangah.ordersystem.newsfeed.domain.enums.AgentType;
import com.joosangah.ordersystem.newsfeed.domain.enums.NewsfeedType;
import com.joosangah.ordersystem.post.domain.dto.response.PostSummaryResponse;
import com.joosangah.ordersystem.user.domain.dto.response.UserResponse;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsfeedResponse {

    private String id;
    private UserResponse user;
    private UserResponse sender;
    private NewsfeedType type;
    private AgentType agentType;
    private PostSummaryResponse post;
    private LocalDateTime createdAt;
}
