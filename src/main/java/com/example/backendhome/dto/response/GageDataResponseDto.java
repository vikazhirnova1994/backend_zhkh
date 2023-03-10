package com.example.backendhome.dto.response;

import com.example.backendhome.entity.enums.TypeGage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GageDataResponseDto {
    private String username;
    private String contractNumber;
    private String address;
    private String serialNumber;
    private TypeGage typeGage;
    private String data;
    private LocalDate departureDate;
}
