package com.example.backendhome.mapper;

import com.example.backendhome.dto.request.FlatRequestDto;
import com.example.backendhome.dto.response.AddressResponseDto;
import com.example.backendhome.dto.response.FlatResponseDto;
import com.example.backendhome.entity.Flat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FlatMapper {
    Flat toFlat(FlatRequestDto flatDto);

    @Mapping(target = "id", source = "flat.id")
    FlatResponseDto toFlatResponseDto(Flat flat);

    @Mapping(target = "address", source = "address")
    AddressResponseDto toAddressResponseDto(String address);
}
