package com.example.backendhome.mapper;

import com.example.backendhome.dto.request.GageRequestDto;
import com.example.backendhome.dto.response.GageResponseDto;
import com.example.backendhome.dto.response.TypeGageResponseDto;
import com.example.backendhome.entity.Gage;
import com.example.backendhome.entity.enums.TypeGage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GageMapper {
    Gage toGage(GageRequestDto gageDto);

    @Mapping(target = "address", expression = "java(gage.getFlat().toString())")
    GageResponseDto toGageResponseDto(Gage gage);

    @Mapping(target = "name", source = "typeGage")
    TypeGageResponseDto toTypeGageResponseDto(TypeGage typeGage);
}
