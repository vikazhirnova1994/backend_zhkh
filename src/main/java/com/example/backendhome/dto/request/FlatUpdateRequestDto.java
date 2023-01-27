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
public class FlatUpdateRequestDto {
    @NotNull
    private String city;
    private String entrance;
    @NotNull
    private String flatNumber;
    @NotNull
    private String  houseNumber;
    @NotNull
    private String id;
    @NotNull
    private String street;
}
