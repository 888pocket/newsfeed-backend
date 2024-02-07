package com.joosangah.newsfeedservice.newsfeed.controller;

import com.joosangah.newsfeedservice.common.client.UserFeignClient;
import com.joosangah.newsfeedservice.common.domain.FollowNewsRequest;
import com.joosangah.newsfeedservice.common.domain.Post;
import com.joosangah.newsfeedservice.common.domain.User;
import com.joosangah.newsfeedservice.newsfeed.domain.dto.response.NewsfeedResponse;
import com.joosangah.newsfeedservice.newsfeed.service.NewsfeedService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/newsfeed")
@RequiredArgsConstructor
public class NewsfeedController {

    private final NewsfeedService newsfeedService;
    private final UserFeignClient userFeignClient;

    @GetMapping("/list")
    public List<NewsfeedResponse> loadNewsfeeds() {
        User user = userFeignClient.getUser();
        return newsfeedService.loadNewsfeeds(user);
    }

    @PostMapping("/internal/post-news")
    public void addPostNews(@RequestBody Post post) {
        User user = userFeignClient.getUser();
        newsfeedService.addPostNews(user, post);
    }

    @PostMapping("/internal/comment-news")
    public void addCommentNews(@RequestBody Post post) {
        User user = userFeignClient.getUser();
        newsfeedService.addCommentNews(user, post);
    }

    @PostMapping("/internal/like-news")
    public void addLikeNews(@RequestBody Post post) {
        User user = userFeignClient.getUser();
        newsfeedService.addLikeNews(user, post);
    }

    @PostMapping("/internal/follow-news")
    public void addFollowNews(@Valid @RequestBody FollowNewsRequest request) {
        newsfeedService.addFollowNews(request.getFollower(), request.getFollow());
    }
}
