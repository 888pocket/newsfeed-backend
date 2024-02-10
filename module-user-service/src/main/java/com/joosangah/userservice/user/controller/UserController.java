package com.joosangah.userservice.user.controller;

import com.joosangah.userservice.auth.service.BlackListService;
import com.joosangah.userservice.file.service.FileService;
import com.joosangah.userservice.user.domain.dto.request.PasswordForm;
import com.joosangah.userservice.user.domain.dto.request.SignupRequest;
import com.joosangah.userservice.user.domain.dto.request.UserForm;
import com.joosangah.userservice.user.domain.dto.response.UserResponse;
import com.joosangah.userservice.user.domain.entity.User;
import com.joosangah.userservice.user.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    @PostMapping("/public/signup")
    public void addUser(@Valid @RequestBody SignupRequest request) {
        // email 중복 검사
        userService.verifyEmail(request.getEmail());
        userService.addUser(request);
    }

    @GetMapping
    public UserResponse loadUser(@RequestHeader("X-Authorization-Id") String userId) {
        User user = userService.loadUser(userId);
        return UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .profileImage(fileService.getFullUrl(user.getProfileImage()))
                .introduction(user.getIntroduction())
                .createdAt(user.getCreatedAt()).build();
    }

    @PutMapping
    public void modifyUser(@RequestHeader("X-Authorization-Id") String userId,
            @Valid @ModelAttribute UserForm request,
            @RequestParam(required = false) MultipartFile profileImageFile) {
        userService.modifyUser(userId, request, profileImageFile);
    }

    @PutMapping("/password")
    public void modifyPassword(@RequestHeader("X-Authorization-Id") String userId,
            @Valid @RequestBody PasswordForm request) {
        userService.modifyPassword(userId, request);

        // 모든 기기에서 로그아웃
        blackListService.addBlacklist(userId);
    }

    @PostMapping("/verify/request")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void sendVerifyEmail(@AuthenticationPrincipal User user) {
        userService.sendVerifyEmail(user);
    }

    @GetMapping("/verify/email")
    public void verifyEmail(@RequestParam String token) {
        userService.confirmEmailVerification(token);
    }

    @PostMapping("/{userId}/follow/toggle")
    public boolean followToggle(@RequestHeader("X-Authorization-Id") String userId, @PathVariable String followId) {
        return userService.toggleFollow(userId, followId);
    }

    @GetMapping("/internal")
    public User loadInternalUser(@RequestHeader("X-Authorization-Id") String userId) {
        return userService.loadUser(userId);
    }
}
