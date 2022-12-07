package com.example.backendhome.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PUBLIC)
@ToString
public class JwtResponse {

    private String token;

    private String type = "Bearer";

    private String refreshToken;

    private UUID id;

    private String username;

    private String contractNumber;

    private List<String> roles;
}
