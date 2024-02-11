package com.joosangah.newsfeedservice.common.client;

import com.joosangah.newsfeedservice.common.domain.User;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service(value = "backendService")
@RequiredArgsConstructor
public class UserFeignService {

    private static final String BACKEND = "backend";

    private final UserFeignClient userFeignClient;

    public User getUser() {
        return userFeignClient.getUser();
    }

    @CircuitBreaker(name = BACKEND, fallbackMethod = "fallback")
    @Retry(name = BACKEND, fallbackMethod = "fallbackRetry")
    public String case1() {
        return userFeignClient.case1();
    }

    @Retry(name = BACKEND, fallbackMethod = "fallbackRetry")
    public String case2() {
        return userFeignClient.case2();
    }

    @CircuitBreaker(name = BACKEND, fallbackMethod = "fallback")
    @Retry(name = BACKEND, fallbackMethod = "fallbackRetry")
    public String case3() {
        return userFeignClient.case3();
    }

    public String fallback(Throwable e) {
        return "fallback Method from UserFeignService, error is " + e.getMessage();
    }
}
