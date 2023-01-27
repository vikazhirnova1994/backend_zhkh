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
public class FlatRequestDto {
    @NotNull
    private String city;
    @NotNull
    private String street;
    @NotNull
    private String houseNumber;
    private Integer entrance;
    @NotNull
    private Integer flatNumber;
}
