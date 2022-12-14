package com.example.backendhome.service;


import com.example.backendhome.dto.payload.JwtResponse;
import com.example.backendhome.dto.payload.LoginRequest;
import com.example.backendhome.entity.RefreshToken;
import com.example.backendhome.security.jwt.JwtUtils;
import com.example.backendhome.util.exception.TokenRefreshException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    public JwtResponse auth(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        //TODO изменить способ сохранения Token-ов на redis
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return JwtResponse.builder()
                .token(jwt)
                .type("Bearer")
                .refreshToken(refreshToken.getToken())
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .contractNumber(userDetails.getContractNumber())
                .roles(roles)
                .build();
    }
    public void logout(){
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!principle.toString().equals("anonymousUser")) {
            UUID userId = ((UserDetailsImpl) principle).getId();
            refreshTokenService.deleteByUserId(userId);
        }
    }
    public String getNewAccessToken(String refreshToken){
        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    return jwtUtils.generateTokenFromUsername(user.getUsername());
                })
                .orElseThrow(() -> new TokenRefreshException(refreshToken, "Refresh token is not in database!"));
    }

}
