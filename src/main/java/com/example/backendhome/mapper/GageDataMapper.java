package com.example.backendhome.mapper;

import com.example.backendhome.dto.request.GageDataRequestDto;
import com.example.backendhome.dto.response.GageDataResponseDto;
import com.example.backendhome.entity.GageData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GageDataMapper {

    GageData toGageData(GageDataRequestDto gageDto);

    @Mapping(target = "serialNumber", source = "gageData.gage.serialNumber")
    @Mapping(target = "typeGage", source = "gageData.gage.typeGage")
    GageDataResponseDto toGageDataResponseDto(GageData gageData);
}
