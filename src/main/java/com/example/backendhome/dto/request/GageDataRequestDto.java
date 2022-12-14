package com.example.backendhome.dto.request;

import com.example.backendhome.entity.enums.TypeGage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GageDataRequestDto {

    @NotNull
   // @JsonProperty("data")
    private String data;

   // @JsonProperty("typeGage")
    @NotNull
    private TypeGage typeGage;
}
