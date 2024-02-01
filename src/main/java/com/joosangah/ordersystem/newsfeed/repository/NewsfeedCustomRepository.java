package com.joosangah.ordersystem.newsfeed.repository;

import com.joosangah.ordersystem.newsfeed.domain.dto.response.NewsfeedResponse;
import com.joosangah.ordersystem.user.domain.entity.User;
import java.util.List;

public interface NewsfeedCustomRepository {

    List<NewsfeedResponse> findAllBySearchParam(User user);
}
