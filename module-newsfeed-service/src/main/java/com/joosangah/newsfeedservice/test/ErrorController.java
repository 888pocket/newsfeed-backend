package com.joosangah.newsfeedservice.test;

import com.joosangah.newsfeedservice.common.client.UserFeignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/newsfeed/public")
@RequiredArgsConstructor
public class ErrorController {

    private final UserFeignService userFeignService;

    @GetMapping("/error/case1")
    public String case1() {
        return userFeignService.case1();
    }

    @GetMapping("/error/case2")
    public String case2() {
        return userFeignService.case2();
    }

    @GetMapping("/error/case3")
    public String case3() {
        return userFeignService.case3();
    }
}
