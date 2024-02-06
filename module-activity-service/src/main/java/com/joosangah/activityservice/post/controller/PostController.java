package com.joosangah.activityservice.post.controller;

import com.joosangah.activityservice.UserFeignService;
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
    private final UserFeignService userFeignService;

    @PostMapping
    public String addPost(@RequestBody PostForm postForm) {
        User user = userFeignService.getUser();
        return postService.addPost(user, postForm);
    }

    @GetMapping("/{postId}")
    public PostResponse loadPost(@PathVariable String postId) {
        return postService.getPost(postId);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable String postId) throws AccessDeniedException {
        User user = userFeignService.getUser();
        postService.softDelete(user.getId(), postId);
    }

    @PostMapping("/{postId}/like/toggle")
    public boolean toggleLike(@PathVariable String postId) {
        User user = userFeignService.getUser();
        return postService.toggleLike(user.getId(), postId);
    }
}
