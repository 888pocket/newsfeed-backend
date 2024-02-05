package com.joosangah.activityservice.post.controller;

import com.joosangah.activityservice.common.domain.User;
import com.joosangah.activityservice.post.domain.dto.request.PostForm;
import com.joosangah.activityservice.post.domain.dto.response.PostResponse;
import com.joosangah.activityservice.post.service.PostService;
import java.nio.file.AccessDeniedException;
import lombok.RequiredArgsConstructor;
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
    public String addPost(@RequestBody PostForm postForm) {
        User user = User.builder().build();
        return postService.addPost(user, postForm);
    }

    @GetMapping("/{postId}")
    public PostResponse loadPost(@PathVariable String postId) {
        return postService.getPost(postId);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable String postId) throws AccessDeniedException {
        User user = User.builder().build();
        postService.softDelete(user.getId(), postId);
    }

    @PostMapping("/{postId}/like/toggle")
    public boolean toggleLike(@PathVariable String postId) {
        User user = User.builder().build();
        return postService.toggleLike(user.getId(), postId);
    }
}
