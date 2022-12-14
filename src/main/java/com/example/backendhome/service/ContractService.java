package com.example.backendhome.service;

import com.example.backendhome.entity.Contract;
import com.example.backendhome.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContractService {

    private final ContractRepository contractRepository;

    public Contract findContract(String contractNumber) {
        return contractRepository.findContractByContractNumber(contractNumber)
                .orElseThrow(() -> new EntityNotFoundException("Error: ContractNumber is not exist"));
    }
    public Contract findContractWithFlat(String contractNumber) {
     return contractRepository.findContractWithFlat(contractNumber)
             .orElseThrow(() -> new EntityNotFoundException("Error: ContractNumber is not exist"));
    }

}
