package com.example.backendhome.mapper;

import com.example.backendhome.dto.request.FlatRequestDto;
import com.example.backendhome.entity.Flat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlatMapper {
    Flat toFlat(FlatRequestDto flatDto);
}
