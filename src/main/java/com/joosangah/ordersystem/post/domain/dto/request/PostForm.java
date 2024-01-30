package com.joosangah.ordersystem.post.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostForm {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
