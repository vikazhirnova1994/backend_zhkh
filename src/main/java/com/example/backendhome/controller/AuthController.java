package com.example.backendhome.controller;

import com.example.backendhome.dto.request.LoginRequest;
import com.example.backendhome.dto.request.RefreshTokenRequest;
import com.example.backendhome.dto.request.SignupRequest;
import com.example.backendhome.dto.response.MessageResponse;
import com.example.backendhome.dto.response.TokenRefreshResponse;
import com.example.backendhome.mapper.UserMapper;
import com.example.backendhome.service.AuthService;
import com.example.backendhome.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserMapper userMapper;
    private final RegisterService registerService;
    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.auth(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        registerService.registerUser(userMapper.toUser(signupRequest),
                signupRequest.getContractNumber());
        return ResponseEntity.ok(new MessageResponse("User CREATED"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        authService.logout();
        return ResponseEntity.ok().body(new MessageResponse("You've been signed out!"));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        return ResponseEntity.ok(TokenRefreshResponse.builder()
                .accessToken(authService.getNewAccessToken(refreshToken))
                .type("Bearer")
                .refreshToken(refreshToken)
                .build());
    }
}
