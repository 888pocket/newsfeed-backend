package com.joosangah.ordersystem.user.controller;

import com.joosangah.ordersystem.auth.service.BlackListService;
import com.joosangah.ordersystem.file.service.FileService;
import com.joosangah.ordersystem.user.domain.dto.request.PasswordForm;
import com.joosangah.ordersystem.user.domain.dto.request.SignupRequest;
import com.joosangah.ordersystem.user.domain.dto.request.UserForm;
import com.joosangah.ordersystem.user.domain.dto.response.UserResponse;
import com.joosangah.ordersystem.user.domain.entity.User;
import com.joosangah.ordersystem.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private final FileService fileService;
    private final BlackListService blackListService;

    @PostMapping("/signup")
    public void addUser(@Valid @RequestBody SignupRequest request) {
        // email 중복 검사
        userService.verifyEmail(request.getEmail());
        userService.addUser(request);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserResponse loadUser(@AuthenticationPrincipal User user) {
        return UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .profileImageUrl(fileService.getFullUrl(user.getProfileImage()))
                .introduction(user.getIntroduction())
                .createdAt(user.getCreatedAt()).build();
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public void modifyUser(@AuthenticationPrincipal User user,
            @Valid @ModelAttribute UserForm request,
            @RequestParam(required = false) MultipartFile profileImageFile) {
        userService.modifyUser(user.getId(), request, profileImageFile);
    }

    @PutMapping("/password")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void modifyPassword(@AuthenticationPrincipal User user,
            @Valid @RequestBody PasswordForm request) {
        userService.modifyPassword(user.getId(), request);

        // 모든 기기에서 로그아웃
        blackListService.addBlacklist(user.getId());
    }
}
