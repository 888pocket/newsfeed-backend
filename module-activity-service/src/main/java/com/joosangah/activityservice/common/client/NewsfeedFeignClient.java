package com.joosangah.activityservice.common.client;

import com.joosangah.activityservice.post.domain.entity.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "NewsfeedClient", url = "http://newsfeed-service:8082/newsfeed/internal", configuration = FeignClientConfiguration.class)
public interface NewsfeedFeignClient {

    @PostMapping("/post-news")
    void addPostNews(Post post);

    @PostMapping("/comment-news")
    void addCommentNews(Post post);

    @PostMapping("/like-news")
    void addLikeNews(Post post);
}