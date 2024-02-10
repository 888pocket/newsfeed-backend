package com.joosangah.activityservice.common.domain;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    private String id;
    private String name;
    private String email;
    private String profileImage;
    private String introduction;
    private List<String> followerIdList;
}
