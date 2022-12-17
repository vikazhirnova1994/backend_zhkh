package com.example.backendhome.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewUserGagesDataDto {

    @NotNull
    private String electricityDay;

    @NotNull
    private String electricityNight;

    @NotNull
    private String waterHot;

    @NotNull
    private String waterCool;

    @NotNull
    private String energy;
}
