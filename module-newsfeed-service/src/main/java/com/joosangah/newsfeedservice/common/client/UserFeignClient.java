package com.joosangah.newsfeedservice.common.client;

import com.joosangah.newsfeedservice.common.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "FeignClient", url = "http://user-service:8080/user", configuration = FeignClientConfiguration.class)
public interface UserFeignClient {

    @GetMapping("/internal")
    User getUser();

    @GetMapping("/errorful/case1")
    ResponseEntity<String> case1();

    @GetMapping("/errorful/case2")
    ResponseEntity<String> case2();

    @GetMapping("/errorful/case3")
    ResponseEntity<String> case3();
}
