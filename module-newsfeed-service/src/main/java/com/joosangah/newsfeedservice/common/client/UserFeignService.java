package com.joosangah.newsfeedservice.common.client;

import com.joosangah.newsfeedservice.common.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFeignService {

    private final UserFeignClient userFeignClient;

    public User getUser() {
        return userFeignClient.getUser();
    }

    public ResponseEntity<String> case1() {
        return userFeignClient.case1();
    }

    public ResponseEntity<String> case2() {
        return userFeignClient.case2();
    }

    public ResponseEntity<String> case3() {
        return userFeignClient.case3();
    }
}
