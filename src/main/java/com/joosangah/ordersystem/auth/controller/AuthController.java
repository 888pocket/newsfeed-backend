package com.joosangah.ordersystem.auth.controller;

import com.joosangah.ordersystem.auth.domain.entity.RefreshToken;
import com.joosangah.ordersystem.auth.security.jwt.JwtUtils;
import com.joosangah.ordersystem.auth.security.payload.request.LoginRequest;
import com.joosangah.ordersystem.auth.security.payload.request.TokenRefreshRequest;
import com.joosangah.ordersystem.auth.security.payload.response.JwtResponse;
import com.joosangah.ordersystem.auth.security.payload.response.TokenRefreshResponse;
import com.joosangah.ordersystem.auth.service.RefreshTokenService;
import com.joosangah.ordersystem.common.exception.TokenRefreshException;
import com.joosangah.ordersystem.user.domain.entity.User;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User userDetails = (User) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(
                new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                        userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromEmail(user.getEmail());
                    refreshTokenService.deleteByToken(requestRefreshToken);
                    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(
                            user.getId());
                    return ResponseEntity.ok(
                            new TokenRefreshResponse(token, newRefreshToken.getToken()));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database"));
    }
}
