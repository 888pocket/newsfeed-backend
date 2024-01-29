package com.joosangah.ordersystem.post.controller;

import com.joosangah.ordersystem.post.domain.dto.request.PostForm;
import com.joosangah.ordersystem.post.domain.dto.response.PostResponse;
import com.joosangah.ordersystem.post.service.PostService;
import com.joosangah.ordersystem.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public String addPost(@AuthenticationPrincipal User user, @RequestBody PostForm postForm) {
        return postService.addPost(user, postForm);
    }

    @GetMapping("/{postId}")
    public PostResponse loadPost(@PathVariable String postId) {
        return postService.getPost(postId);
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deletePost(@AuthenticationPrincipal User user, @PathVariable String postId) {
        postService.softDelete(user.getId(), postId);
    }
}
