package com.example.backendhome.mapper;

import com.example.backendhome.dto.request.GageRequestDto;
import com.example.backendhome.entity.Gage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GageMapper {
    Gage toGage(GageRequestDto gageDto);
}
