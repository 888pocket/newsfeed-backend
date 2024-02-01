package com.joosangah.ordersystem.newsfeed.service;

import com.joosangah.ordersystem.newsfeed.domain.dto.response.NewsfeedResponse;
import com.joosangah.ordersystem.newsfeed.repository.NewsfeedCustomRepository;
import com.joosangah.ordersystem.user.domain.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsfeedService {

    private final NewsfeedCustomRepository newsfeedCustomRepository;

    public List<NewsfeedResponse> loadNewsfeeds(User user) {
        return newsfeedCustomRepository.findAllBySearchParam(user);
    }
}
