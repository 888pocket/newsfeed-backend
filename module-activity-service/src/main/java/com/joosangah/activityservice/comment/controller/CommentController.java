package com.joosangah.activityservice.comment.controller;

import com.joosangah.activityservice.UserFeignService;
import com.joosangah.activityservice.comment.domain.dto.request.CommentForm;
import com.joosangah.activityservice.comment.service.CommentService;
import com.joosangah.activityservice.common.domain.User;
import lombok.RequiredArgsConstructor;
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
    private final UserFeignService userFeignService;

    @PostMapping("/{targetId}")
    public void addComment(@PathVariable String targetId,
            @RequestBody CommentForm commentForm) {
        User user = userFeignService.getUser();
        commentService.addComment(user, targetId, commentForm);
    }
}
