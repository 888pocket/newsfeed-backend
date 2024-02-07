package com.joosangah.newsfeedservice.common.domain;

import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    @NotBlank
    private String id;
    private String name;
    private String email;
    private String profileImage;
    private String introduction;
    private List<String> followerIdList;
}
