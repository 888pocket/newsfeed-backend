package com.joosangah.ordersystem.user.service;

import com.joosangah.ordersystem.auth.domain.enums.ERole;
import com.joosangah.ordersystem.auth.security.WebSecurityConfig;
import com.joosangah.ordersystem.auth.service.RoleService;
import com.joosangah.ordersystem.common.exception.DuplicateException;
import com.joosangah.ordersystem.file.service.FileService;
import com.joosangah.ordersystem.user.domain.dto.request.PasswordForm;
import com.joosangah.ordersystem.user.domain.dto.request.SignupRequest;
import com.joosangah.ordersystem.user.domain.dto.request.UserForm;
import com.joosangah.ordersystem.user.domain.entity.User;
import com.joosangah.ordersystem.user.repository.UserRepository;
import java.util.Collections;
import java.util.HashSet;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleService roleService;
    private final FileService fileService;

    private final WebSecurityConfig webSecurityConfig;

    public User loadUser(String userId) {
        return userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
    }

    public void addUser(SignupRequest request) {
        PasswordEncoder passwordEncoder = webSecurityConfig.passwordEncoder();
        User newUser = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .profileImage("profile-image/img_profile-dummy.png")
                .roles(new HashSet<>(Collections.singletonList(
                        roleService.loadRole(ERole.ROLE_USER.getAuthority())))).build();
        userRepository.save(newUser);
    }

    public void verifyEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicateException();
        }
    }

    @Transactional
    public void modifyUser(String userId, UserForm request, MultipartFile profileImageFile) {
        User findUser = loadUser(userId);
        findUser.modify(request);

        String fileUrl = "profile-image/img_profile-dummy.png";
        if (profileImageFile != null) {
            fileUrl = fileService.uploadImage(profileImageFile,
                    "profile-image/" + userId + "/");
        } else if (request.getProfileImageUrl() != null) {
            fileUrl = request.getProfileImageUrl();
        }

        findUser.modifyProfileImage(fileUrl);

        userRepository.save(findUser);
    }

    @Transactional
    public void modifyPassword(String userId, PasswordForm request) {
        PasswordEncoder passwordEncoder = webSecurityConfig.passwordEncoder();

        User findUser = loadUser(userId);
        findUser.modifyPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(findUser);
    }
}
