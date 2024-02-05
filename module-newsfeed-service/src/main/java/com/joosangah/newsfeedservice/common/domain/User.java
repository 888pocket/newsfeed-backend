package com.joosangah.newsfeedservice.common.domain;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String profileImage;
    private String introduction;
    private List<String> followerIdList;

    @Builder
    public User(String id, String name, String email, String password, String profileImage,
            String introduction, List<String> followerIdList) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.introduction = introduction;
        this.followerIdList = followerIdList;
    }
}
