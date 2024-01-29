package com.joosangah.ordersystem.comment.controller;

import com.joosangah.ordersystem.comment.domain.dto.request.CommentForm;
import com.joosangah.ordersystem.comment.service.CommentService;
import com.joosangah.ordersystem.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{targetId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void addComment(@AuthenticationPrincipal User user, @PathVariable String targetId,
            @RequestBody CommentForm commentForm) {
        commentService.addComment(user, targetId, commentForm);
    }
}
