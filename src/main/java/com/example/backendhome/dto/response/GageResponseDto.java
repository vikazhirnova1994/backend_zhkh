package com.example.backendhome.dto.response;

import com.example.backendhome.entity.enums.TypeGage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GageResponseDto {
    private UUID id;
    private String serialNumber;
    private TypeGage typeGage;
    private String manufacturer;
    private LocalDate installationDate;
    private String address;
}
