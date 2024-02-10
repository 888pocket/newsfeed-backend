package com.joosangah.newsfeedservice.common.client;

import com.joosangah.newsfeedservice.common.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFeignService {

    private final UserFeignClient userFeignClient;

    public User getUser() {
        return userFeignClient.getUser();
    }
}
