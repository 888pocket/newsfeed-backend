package com.joosangah.ordersystem.follow.controller;

import com.joosangah.ordersystem.follow.service.FollowService;
import com.joosangah.ordersystem.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/toggle/{targetId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public boolean toggleFollow(@AuthenticationPrincipal User user, @PathVariable String targetId) {
        return followService.toggleFollow(user, targetId);
    }
}
