package com.joosangah.ordersystem.interaction.controller;

import com.joosangah.ordersystem.interaction.domain.dto.request.InteractionRequest;
import com.joosangah.ordersystem.interaction.service.InteractionService;
import com.joosangah.ordersystem.user.domain.entity.User;
import com.joosangah.ordersystem.user.domain.enums.InteractionType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/interaction")
@RequiredArgsConstructor
public class InteractionController {

    private final InteractionService interactionService;

    @PostMapping("/follow/toggle")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void toggleFollow(@AuthenticationPrincipal User user,
            @RequestBody InteractionRequest request) {
        interactionService.toggle(user.getId(), request.getTargetId(), InteractionType.FOLLOW);
    }

    @PostMapping("/like/post/toggle")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void toggleLikePost(@AuthenticationPrincipal User user,
            @RequestBody InteractionRequest request) {
        interactionService.toggle(user.getId(), request.getTargetId(), InteractionType.LIKE_POST);
    }

    @PostMapping("/like/comment/toggle")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void toggleLikeComment(@AuthenticationPrincipal User user,
            @RequestBody InteractionRequest request) {
        interactionService.toggle(user.getId(), request.getTargetId(),
                InteractionType.LIKE_COMMENT);
    }
}
