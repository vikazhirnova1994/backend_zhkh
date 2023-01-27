package com.example.backendhome.mapper;

import com.example.backendhome.dto.response.ClaimResponseDto;
import com.example.backendhome.dto.response.UserClaimResponseDto;
import com.example.backendhome.entity.Claim;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClaimMapper {
    @Mapping(target = "id", source = "claim.id")
    @Mapping(target = "executorIdentificationNumber", source = "claim.executorIdentificationNumber")
    @Mapping(target = "creationDate", source = "claim.creationDate")
    @Mapping(target = "completionDate", source = "claim.completionDate")
    UserClaimResponseDto toUserClaimResponseDto(Claim claim);

    @Mapping(target = "id", source = "claim.id")
    @Mapping(target = "contractNumber", source = "claim.user.contract.contractNumber")
    @Mapping(target = "address",  expression = "java( claim.getUser().getContract().getFlat().toString() )")
    @Mapping(target = "executorIdentificationNumber", source = "claim.executorIdentificationNumber")
    @Mapping(target = "creationDate", source = "claim.creationDate")
    @Mapping(target = "completionDate", source = "claim.completionDate")
    ClaimResponseDto toClaimResponseDto(Claim claim);
}
