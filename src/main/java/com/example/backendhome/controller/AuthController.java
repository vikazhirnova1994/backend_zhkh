package com.example.backendhome.controller;


import com.example.backendhome.entity.Contract;
import com.example.backendhome.entity.RefreshToken;
import com.example.backendhome.entity.User;
import com.example.backendhome.payload.JwtResponse;
import com.example.backendhome.payload.LoginRequest;
import com.example.backendhome.payload.MessageResponse;
import com.example.backendhome.payload.RefreshTokenRequest;
import com.example.backendhome.payload.SignupRequest;
import com.example.backendhome.payload.TokenRefreshResponse;
import com.example.backendhome.repository.ContractRepository;
import com.example.backendhome.repository.UserRepository;
import com.example.backendhome.security.jwt.JwtUtils;
import com.example.backendhome.service.RefreshTokenService;
import com.example.backendhome.service.RoleCreater;
import com.example.backendhome.service.UserDetailsImpl;
import com.example.backendhome.util.TokenRefreshException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final ContractRepository contractRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final RoleCreater roleCreater;

    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        //TODO поменять генерацию RefreshToken на использования алгоритма
        //TODO решить где лучше хранить Token-ы (использовать ли куки)
        //TODO изменить способ сохранения Token-ов на redis
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        //ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.getToken());

        return ResponseEntity.ok(
                JwtResponse.builder()
                        .token(jwt)
                        .type("Bearer")
                        .refreshToken(refreshToken.getToken())
                        .id(userDetails.getId())
                        .username(userDetails.getUsername())
                        .contractNumber(userDetails.getContractNumber())
                        .roles(roles)
                        .build())
                // .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                //// .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                ////.body(new UserInfoResponse(userDetails.getId(),
                ////userDetails.getUsername(),
                //// userDetails.getEmail(),
                ////roles));
                ;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {

        //TODO вынести в сервисный класс
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is exist"));
        }
        Contract contract = null;
        String contractNumber = signupRequest.getContractNumber();

        if (!contractRepository.existsContractByContractNumber(contractNumber)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: ContractNumber is not exist"));
        } else {
            contract = contractRepository.findContractByContractNumber(contractNumber)
                    .orElseThrow(() -> new EntityNotFoundException(""));
        }
        //TODO добавить маппер MapStruct для преобразования SignupRequest  в User
        User user = User.builder()
                .username(signupRequest.getUsername())
                .password(encoder.encode(signupRequest.getPassword()))
                .build();

        user.setRoles(roleCreater.getRole(contractNumber));
        user.setContract(contract);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User CREATED"));
    }

    //пример для сессии
    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!principle.toString().equals("anonymousUser")) {
            UUID userId = ((UserDetailsImpl) principle).getId();
            refreshTokenService.deleteByUserId(userId);
        }
        ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();
        ResponseCookie jwtRefreshCookie = jwtUtils.getCleanJwtRefreshCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    //ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);
                    return ResponseEntity.ok(TokenRefreshResponse.builder()
                            .accessToken(token)
                            .type("Bearer")
                            .refreshToken(requestRefreshToken)
                            .build())
                            //для вставки куки
                            //.header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                            //.header(HttpHeaders.SET_COOKIE, refreshToken)
                            //.body(new MessageResponse("Token is refreshed successfully!"));
                            ;
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }
}
