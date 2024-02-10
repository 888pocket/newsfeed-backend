package com.joosangah.newsfeedservice.common.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowNewsRequest {

    private User follower;
    private User follow;
}
