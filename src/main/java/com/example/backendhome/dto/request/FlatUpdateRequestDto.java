package com.example.backendhome.dto.request;

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
public class FlatUpdateRequestDto {
    private String city;
    private String entrance;
    private String flatNumber;
    private String  houseNumber;
    private String id;
    private String street;
}
