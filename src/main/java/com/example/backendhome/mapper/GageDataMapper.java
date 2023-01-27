package com.example.backendhome.mapper;

import com.example.backendhome.dto.request.GageDataRequestDto;
import com.example.backendhome.dto.response.GageDataResponseDto;
import com.example.backendhome.dto.response.UserGageDataResponseDto;
import com.example.backendhome.entity.GageData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GageDataMapper {

    GageData toGageData(GageDataRequestDto gageDto);

    @Mapping(target = "serialNumber", source = "gageData.gage.serialNumber")
    @Mapping(target = "typeGage", source = "gageData.gage.typeGage")
    UserGageDataResponseDto toUserGageDataResponseDto(GageData gageData);

    @Mapping(target = "username", source = "gageData.user.username")
    @Mapping(target = "contractNumber", source = "gageData.user.contract.contractNumber")
    @Mapping(target = "address",  expression = "java( gageData.getUser().getContract().getFlat().toString() )")
    @Mapping(target = "serialNumber", source = "gageData.gage.serialNumber")
    @Mapping(target = "typeGage", source = "gageData.gage.typeGage")
    GageDataResponseDto toGageDataResponseDto(GageData gageData);

}
