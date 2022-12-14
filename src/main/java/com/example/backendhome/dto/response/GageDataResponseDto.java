package com.example.backendhome.dto.response;

import com.example.backendhome.entity.enums.TypeGage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GageDataResponseDto {

    @NotNull
    private String serialNumber;

    @NotNull
    private TypeGage typeGage;

    @NotNull
    private String data;

    @NotNull
    private LocalDate departureDate;
}
