package com.example.backendhome.dto.request;

import com.example.backendhome.entity.enums.TypeGage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

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
public class GageRequestWithAddressDto {
    @NotNull
    @Size(max = 20)
    private String serialNumber;

    @NotNull
    private TypeGage typeGage;

    @NotNull
    @Size(max = 20)
    private String manufacturer;

    private InstallationDate installationDate;

    private String address;
}
