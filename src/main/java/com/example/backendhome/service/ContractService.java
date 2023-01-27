package com.example.backendhome.service;

import com.example.backendhome.dto.request.ContractRequestDto;
import com.example.backendhome.entity.Contract;
import com.example.backendhome.entity.Flat;
import com.example.backendhome.repository.ContractRepository;
import com.example.backendhome.service.generator.AddressSplitter;
import com.example.backendhome.service.generator.DateTransform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContractService {
    private final ContractRepository contractRepository;
    private final FlatService flatService;
    private final DateTransform dateTransform;
    private final AddressSplitter addressSplitter;

    public List<Contract> getContracts() {
        return contractRepository.findAll();
    }

    public Contract findContract(String contractNumber) {
        return contractRepository.findContractByContractNumber(contractNumber)
                .orElseThrow(() -> new EntityNotFoundException("Error: ContractNumber is not exist"));
    }
    public Contract findContractWithFlat(String contractNumber) {
     return contractRepository.findContractWithFlat(contractNumber)
             .orElseThrow(() -> new EntityNotFoundException("Error: ContractNumber is not exist"));
    }

    public Slice<Contract> getContractsPage(int page, int size) {
        Pageable of = PageRequest.of(page, size);
        return contractRepository.findContract(of);
    }

    @Transactional
    public void createContract(ContractRequestDto dto) {
        log.info("Transforming dto...");
        log.info("Getting information about flat from dto...");
        String address = dto.getAddress();
        Flat flat = flatService.getFlat(
                addressSplitter.getCity(address),
                addressSplitter.getStreet(address),
                addressSplitter.getHouseNumber(address),
                addressSplitter.getEntrance(address),
                addressSplitter.getFlatNumber(address));

        log.info("Getting gage installation date from dto...");
        LocalDate signedDate = dateTransform.toTransform(dto.getSignedDate());

        Contract newContract = Contract.builder()
                .contractNumber(dto.getContractNumber())
                .flat(flat)
                .signedDate(signedDate)
                .build();

        contractRepository.save(newContract);
    }

    @Transactional
    public void deleteContract(UUID id) {
        log.info("Finding gage by id: {}", id);
        Contract contract = contractRepository.findById(id).orElseThrow();
        contract.setTerminationDate(LocalDate.now());
        log.info("Changing termination date for contract: {}", contract);
        contractRepository.save(contract);
    }
}
