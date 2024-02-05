package com.joosangah.newsfeedservice.common.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Post extends AuditEntity {

    private String id;
    private String userId;
    private String title;
    private String content;
    private List<String> likeUserIdList;

    @Builder
    public Post(String userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.likeUserIdList = new ArrayList<>();
    }
}
