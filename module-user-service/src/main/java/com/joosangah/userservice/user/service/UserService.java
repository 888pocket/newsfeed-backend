package com.joosangah.userservice.user.service;

import com.joosangah.userservice.MailService;
import com.joosangah.userservice.auth.domain.enums.ERole;
import com.joosangah.userservice.auth.security.WebSecurityConfig;
import com.joosangah.userservice.auth.service.RoleService;
import com.joosangah.userservice.common.exception.DuplicateException;
import com.joosangah.userservice.common.util.TokenGenerator;
import com.joosangah.userservice.file.service.FileService;
import com.joosangah.userservice.user.domain.dto.request.PasswordForm;
import com.joosangah.userservice.user.domain.dto.request.SignupRequest;
import com.joosangah.userservice.user.domain.dto.request.UserForm;
import com.joosangah.userservice.user.domain.entity.User;
import com.joosangah.userservice.user.domain.entity.VerificationToken;
import com.joosangah.userservice.user.repository.UserRepository;
import com.joosangah.userservice.user.repository.VerificationTokenRepository;
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
    private final VerificationTokenRepository verificationTokenRepository;


    private final RoleService roleService;
    private final FileService fileService;
    private final MailService mailService;
//    private final NewsfeedService newsfeedService;

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

    @Transactional
    public void sendVerifyEmail(User user) {
        VerificationToken token = new VerificationToken(user.getId(),
                TokenGenerator.generateCode());
        verificationTokenRepository.save(token);
        mailService.sendEmail(user.getEmail(), "인증코드 발송",
                "인증하기 -> http://localhost:8080/user/verify/email?token=" + token.getToken()
                        + " 클릭");
    }

    @Transactional
    public void confirmEmailVerification(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(NoSuchElementException::new);
        User findUser = loadUser(verificationToken.getUserId());
        findUser.verifyEmail();
        userRepository.save(findUser);
    }

    public boolean toggleFollow(User user, String followId) {
        User follow = loadUser(followId);
        if(follow.getFollowerIdList().contains(user.getId())) {
            follow.getFollowerIdList().remove(user.getId());
            userRepository.save(follow);
            return false;
        }

        follow.getFollowerIdList().add(user.getId());
        userRepository.save(follow);

//        newsfeedService.addFollowNews(user, follow);
        return true;
    }
}
