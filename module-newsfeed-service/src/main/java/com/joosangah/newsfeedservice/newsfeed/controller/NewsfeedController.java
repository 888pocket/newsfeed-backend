package com.joosangah.newsfeedservice.newsfeed.controller;

import com.joosangah.newsfeedservice.UserFeignService;
import com.joosangah.newsfeedservice.common.domain.User;
import com.joosangah.newsfeedservice.newsfeed.domain.dto.response.NewsfeedResponse;
import com.joosangah.newsfeedservice.newsfeed.service.NewsfeedService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/newsfeed")
@RequiredArgsConstructor
public class NewsfeedController {

    private final NewsfeedService newsfeedService;
    private final UserFeignService userFeignService;

    @GetMapping("/list")
    public List<NewsfeedResponse> loadNewsfeeds() {
        User user = userFeignService.getUser();
        return newsfeedService.loadNewsfeeds(user);
    }
}
