package com.joosangah.userservice.user.domain.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserForm {

    @NotBlank
    private String name;
    private String profileImageUrl;
    private String introduction;
}
