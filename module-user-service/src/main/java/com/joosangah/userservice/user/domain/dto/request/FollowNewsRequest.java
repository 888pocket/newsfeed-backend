package com.joosangah.userservice.user.domain.dto.request;

import com.joosangah.userservice.user.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowNewsRequest {

    private User follower;
    private User follow;
}
