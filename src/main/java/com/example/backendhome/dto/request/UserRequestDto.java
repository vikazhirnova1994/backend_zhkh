package com.example.backendhome.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @NotNull
    @Size(min = 3, max = 20)
    private String username;
    @Size(min = 14, max = 14)
    private String contractNumber;
    private String role;
    @NotNull
    @Size(min = 8, max = 40)
    private String password;
}
