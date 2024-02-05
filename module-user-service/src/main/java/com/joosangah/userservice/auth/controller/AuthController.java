package com.joosangah.userservice.auth.controller;

import com.joosangah.userservice.auth.domain.entity.RefreshToken;
import com.joosangah.userservice.auth.security.jwt.JwtUtils;
import com.joosangah.userservice.auth.security.payload.request.LoginRequest;
import com.joosangah.userservice.auth.security.payload.request.TokenRefreshRequest;
import com.joosangah.userservice.auth.security.payload.response.JwtResponse;
import com.joosangah.userservice.auth.security.payload.response.TokenRefreshResponse;
import com.joosangah.userservice.auth.service.BlackListService;
import com.joosangah.userservice.auth.service.RefreshTokenService;
import com.joosangah.userservice.common.exception.TokenRefreshException;
import com.joosangah.userservice.user.domain.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final RefreshTokenService refreshTokenService;
    private final BlackListService blackListService;

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

        blackListService.deleteBlacklist(userDetails.getId());

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

    @PostMapping("/signout")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void signOut(@RequestBody String refreshToken) {
        SecurityContextHolder.clearContext();
        refreshTokenService.deleteByToken(refreshToken);
    }

    @PostMapping("/signout-all")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void signOutAll(@AuthenticationPrincipal User user) {
        SecurityContextHolder.clearContext();
        refreshTokenService.deleteByUserId(user.getId());
        blackListService.addBlacklist(user.getId());
    }
}
