package com.joosangah.newsfeedservice.common.domain;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Post extends AuditEntity {

    private String id;
    private String userId;
    private String title;
    private String content;
    private List<String> likeUserIdList;
}
