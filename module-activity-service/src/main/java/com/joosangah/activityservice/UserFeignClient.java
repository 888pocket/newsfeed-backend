package com.joosangah.activityservice;

import com.joosangah.activityservice.common.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "UserClient", url = "http://user-service:8080/user/internal", configuration = FeignClientConfiguration.class)
public interface UserFeignClient {

    @GetMapping
    User getUser();
}
