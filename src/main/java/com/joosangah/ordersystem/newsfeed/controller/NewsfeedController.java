package com.joosangah.ordersystem.newsfeed.controller;

import com.joosangah.ordersystem.newsfeed.domain.dto.response.NewsfeedResponse;
import com.joosangah.ordersystem.newsfeed.service.NewsfeedService;
import com.joosangah.ordersystem.user.domain.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/newsfeed")
@RequiredArgsConstructor
public class NewsfeedController {

    private final NewsfeedService newsfeedService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<NewsfeedResponse> loadNewsfeeds(@AuthenticationPrincipal User user) {
        return newsfeedService.loadNewsfeeds(user);
    }
}
