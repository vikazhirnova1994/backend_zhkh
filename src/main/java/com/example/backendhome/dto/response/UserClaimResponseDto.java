package com.example.backendhome.dto.response;

import com.example.backendhome.entity.enums.ClaimStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserClaimResponseDto {
    private UUID id;
    private String description;
    private String executorIdentificationNumber;
    private LocalDate creationDate;
    private LocalDate completionDate;
    private ClaimStatus status;
}
