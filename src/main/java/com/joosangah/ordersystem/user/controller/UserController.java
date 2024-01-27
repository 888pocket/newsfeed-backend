package com.joosangah.ordersystem.user.controller;

import com.joosangah.ordersystem.common.exception.DuplicateException;
import com.joosangah.ordersystem.user.domain.dto.request.SignupRequest;
import com.joosangah.ordersystem.user.domain.dto.request.UserForm;
import com.joosangah.ordersystem.user.domain.entity.User;
import com.joosangah.ordersystem.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public void addUser(@Valid @RequestBody SignupRequest request) {
        // email 중복 검사
        if (userService.isDuplicateEmail(request.getEmail())) {
            throw new DuplicateException();
        }
        userService.addUser(request);
    }

    @PutMapping("/{userId}")
//    @PreAuthorize("hasRole('USER')")
    public void modifyUser(@AuthenticationPrincipal User member, @PathVariable String userId,
            @Valid @ModelAttribute UserForm request,
            @RequestParam(required = false) MultipartFile profileImageFile) {
        userService.modifyUser(userId, request, profileImageFile);
    }
}
