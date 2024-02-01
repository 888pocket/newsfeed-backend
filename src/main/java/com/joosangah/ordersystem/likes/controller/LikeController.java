package com.joosangah.ordersystem.likes.controller;

import com.joosangah.ordersystem.likes.domain.enums.LikeType;
import com.joosangah.ordersystem.likes.service.LikeService;
import com.joosangah.ordersystem.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/post/toggle/{targetId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public boolean togglePostLike(@AuthenticationPrincipal User user,
            @PathVariable String targetId) {
        return likeService.toggleLike(user, targetId, LikeType.POST);
    }

    @PostMapping("/comment/toggle/{targetId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public boolean toggleCommentLike(@AuthenticationPrincipal User user,
            @PathVariable String targetId) {
        return likeService.toggleLike(user, targetId, LikeType.COMMENT);
    }
}
