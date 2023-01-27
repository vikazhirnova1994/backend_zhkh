package com.example.backendhome.mapper;

import com.example.backendhome.dto.request.ContractRequestDto;
import com.example.backendhome.dto.response.ContractResponseDto;
import com.example.backendhome.entity.Contract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    @Mapping(target = "address",  expression = "java( contract.getFlat().toString() )")
    ContractResponseDto toContractResponseDto(Contract contract);
}
