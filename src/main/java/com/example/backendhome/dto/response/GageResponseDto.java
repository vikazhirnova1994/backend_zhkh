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

/**
 * @author Victoria Zhirnova
 * @project backendHome
 */

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GageResponseDto {

    UUID id;
    String serialNumber;
    TypeGage typeGage;

    String manufacturer;

    LocalDate installationDate;

 /*   LocalDate disposalDate;*/

    String address;
}
