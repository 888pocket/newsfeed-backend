package com.joosangah.userservice;

import com.joosangah.userservice.user.domain.dto.request.FollowNewsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "FeignClient", url = "http://newsfeed-service:8082/newsfeed/internal", configuration = FeignClientConfiguration.class)
public interface NewsfeedFeignClient {

    @PostMapping("/follow-news")
    void addFollowNews(FollowNewsRequest request);
}
