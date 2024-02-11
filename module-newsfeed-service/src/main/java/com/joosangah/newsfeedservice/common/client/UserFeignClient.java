package com.joosangah.newsfeedservice.common.client;

import com.joosangah.newsfeedservice.common.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "FeignClient", url = "http://user-service:8080/user", configuration = FeignClientConfiguration.class)
public interface UserFeignClient {

    @GetMapping("/internal")
    User getUser();

    @GetMapping("/public/errorful/case1")
    String case1();

    @GetMapping("/public/errorful/case2")
    String case2();

    @GetMapping("/public/errorful/case3")
    String case3();
}
