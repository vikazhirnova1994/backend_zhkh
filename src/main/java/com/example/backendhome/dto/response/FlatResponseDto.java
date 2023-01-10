package com.example.backendhome.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FlatResponseDto {
    private String id;
    private String city;
    private String street;
    private String  houseNumber;
    private Integer entrance;
    private Integer flatNumber;
}
