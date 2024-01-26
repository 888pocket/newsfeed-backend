package com.joosangah.ordersystem.user.controller;

import com.joosangah.ordersystem.user.domain.dto.request.UserRequest;
import com.joosangah.ordersystem.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("join")
    public String addUser(@Valid @RequestBody UserRequest request) {
        // email 중복 검사
        userService.isDuplicateEmail(request.getEmail());

        return userService.addUser(request);
    }
}
