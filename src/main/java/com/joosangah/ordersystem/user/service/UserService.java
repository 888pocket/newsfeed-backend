package com.joosangah.ordersystem.user.service;

import com.joosangah.ordersystem.auth.domain.enums.ERole;
import com.joosangah.ordersystem.auth.repository.RoleRepository;
import com.joosangah.ordersystem.auth.security.WebSecurityConfig;
import com.joosangah.ordersystem.user.domain.dto.request.UserRequest;
import com.joosangah.ordersystem.user.domain.entity.User;
import com.joosangah.ordersystem.user.repository.UserRepository;
import java.util.Collections;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final WebSecurityConfig webSecurityConfig;

    public String addUser(UserRequest request) {
        PasswordEncoder passwordEncoder = webSecurityConfig.passwordEncoder();
        User newUser = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(new HashSet<>(Collections.singletonList(
                        roleRepository.findByName(ERole.USER.getAuthority()).get()))).build();
        userRepository.save(newUser);
        return newUser.getPassword();
    }

    public Boolean isDuplicateEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
