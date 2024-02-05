package com.joosangah.newsfeedservice.newsfeed.controller;

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

    @GetMapping("/list")
    public List<NewsfeedResponse> loadNewsfeeds() {
        User user = User.builder().build();
        return newsfeedService.loadNewsfeeds(user);
    }
}
